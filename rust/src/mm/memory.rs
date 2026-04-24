use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

const GB_FACTOR: f64 = 1_000_000_000.0;

#[derive(Debug, Default)]
pub struct RamData {
    pub total: f64,
    pub available: f64,
    pub used: f64,
    pub used_percentage: f64,
}

#[derive(Debug, Default)]
pub struct ZramData {
    pub is_active: bool,
    pub total: f64,
    pub available: f64,
    pub used: f64,
    pub used_percentage: f64,
}

fn format_to_two_decimals(value: f64) -> f64 {
    (value * 100.0).round() / 100.0
}

fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where
    P: AsRef<Path>,
{
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}

pub fn get_memory_data() -> (RamData, ZramData) {
    let mut mem_total_bytes = 0_f64;
    let mut mem_available_bytes = 0_f64;
    let mut swap_total_bytes = 0_f64;
    let mut swap_free_bytes = 0_f64;

    if let Ok(lines) = read_lines("/proc/meminfo") {
        for line in lines.flatten() {
            let parts: Vec<&str> = line.split_whitespace().collect();
            if parts.len() >= 2 {
                let key = parts[0];
                let val_kb: f64 = parts[1].parse().unwrap_or(0.0);
                let val_bytes = val_kb * 1024.0;

                match key {
                    "MemTotal:" => mem_total_bytes = val_bytes,
                    "MemAvailable:" => mem_available_bytes = val_bytes,
                    "SwapTotal:" => swap_total_bytes = val_bytes,
                    "SwapFree:" => swap_free_bytes = val_bytes,
                    _ => {}
                }
            }
        }
    } else {
        println!("Failed to read /proc/meminfo");
    }

    let ram_used_bytes = mem_total_bytes - mem_available_bytes;
    let ram_percentage = if mem_total_bytes > 0.0 {
        (ram_used_bytes / mem_total_bytes) * 100.0
    } else {
        0.0
    };

    let ram_data = RamData {
        total: format_to_two_decimals(mem_total_bytes / GB_FACTOR),
        available: format_to_two_decimals(mem_available_bytes / GB_FACTOR),
        used: format_to_two_decimals(ram_used_bytes / GB_FACTOR),
        used_percentage: format_to_two_decimals(ram_percentage),
    };

    let swap_used_bytes = swap_total_bytes - swap_free_bytes;
    let swap_percentage = if swap_total_bytes > 0.0 {
        (swap_used_bytes / swap_total_bytes) * 100.0
    } else {
        0.0
    };

    let zram_data = ZramData {
        is_active: swap_total_bytes > 0.0,
        total: format_to_two_decimals(swap_total_bytes / GB_FACTOR),
        available: format_to_two_decimals(swap_free_bytes / GB_FACTOR),
        used: format_to_two_decimals(swap_used_bytes / GB_FACTOR),
        used_percentage: format_to_two_decimals(swap_percentage),
    };

    (ram_data, zram_data)
}
