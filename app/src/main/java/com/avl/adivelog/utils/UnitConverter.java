package com.avl.adivelog.utils;


import com.avl.adivelog.R;
import com.avl.adivelogapp;

import java.text.DecimalFormat;

/**
 * Helper class to convert values to different units.
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 * @author Alexander von Lünen
 */
public class UnitConverter {

   public static final String[] SYSTEMS = {"si", "metric", "imperial", "display", "display-imperial"};

   private static final String[] TEMPERATURE_UNITS = {adivelogapp.getAppContext().getString(R.string.unit_kelvin),
         adivelogapp.getAppContext().getString(R.string.unit_degree_celsius),
         adivelogapp.getAppContext().getString(R.string.unit_degree_fahrenheit),
         adivelogapp.getAppContext().getString(R.string.unit_degree_celsius),
         adivelogapp.getAppContext().getString(R.string.unit_degree_fahrenheit)};
   private static final String[] TIME_UNITS = {adivelogapp.getAppContext().getString(R.string.unit_second),
         adivelogapp.getAppContext().getString(R.string.unit_minute),
         adivelogapp.getAppContext().getString(R.string.unit_minute),
         adivelogapp.getAppContext().getString(R.string.unit_minute),
         adivelogapp.getAppContext().getString(R.string.unit_minute)};
   private static final String[] ALTITUDE_UNITS = {adivelogapp.getAppContext().getString(R.string.unit_meter),
         adivelogapp.getAppContext().getString(R.string.unit_meter),
         adivelogapp.getAppContext().getString(R.string.unit_feet),
         adivelogapp.getAppContext().getString(R.string.unit_meter),
         adivelogapp.getAppContext().getString(R.string.unit_feet)};
   private static final String[] PRESSURE_UNITS = {adivelogapp.getAppContext().getString(R.string.unit_pascal),
         adivelogapp.getAppContext().getString(R.string.unit_bar),
         adivelogapp.getAppContext().getString(R.string.unit_psi),
         adivelogapp.getAppContext().getString(R.string.unit_bar),
         adivelogapp.getAppContext().getString(R.string.unit_psi)};
   private static final String[] VOLUME_UNITS = {adivelogapp.getAppContext().getString(R.string.unit_cubic_meter),
         adivelogapp.getAppContext().getString(R.string.unit_cubic_meter),
         adivelogapp.getAppContext().getString(R.string.unit_cubic_feet),
         adivelogapp.getAppContext().getString(R.string.unit_liter),
         adivelogapp.getAppContext().getString(R.string.unit_cubic_feet)};
   private static final String[] WEIGHT_UNITS = {adivelogapp.getAppContext().getString(R.string.unit_kilo),
         adivelogapp.getAppContext().getString(R.string.unit_kilo),
         adivelogapp.getAppContext().getString(R.string.unit_kilo),
         adivelogapp.getAppContext().getString(R.string.unit_kilo),
         adivelogapp.getAppContext().getString(R.string.unit_kilo)};
   private static final String[] AMV_UNITS = {adivelogapp.getAppContext().getString(R.string.unit_cubic_meter_per_second),
         adivelogapp.getAppContext().getString(R.string.unit_cubic_meter_per_minute),
         adivelogapp.getAppContext().getString(R.string.unit_liter_per_minute),
         adivelogapp.getAppContext().getString(R.string.unit_liter_per_minute),
         adivelogapp.getAppContext().getString(R.string.unit_liter_per_minute)};
   private static final String[] SPEED_UNITS = {adivelogapp.getAppContext().getString(R.string.unit_meter_per_second),
         adivelogapp.getAppContext().getString(R.string.unit_meter_per_minute),
         adivelogapp.getAppContext().getString(R.string.unit_meter_per_minute),
         adivelogapp.getAppContext().getString(R.string.unit_meter_per_minute),
         adivelogapp.getAppContext().getString(R.string.unit_meter_per_minute)};

   public static final int SYSTEM_SI = 0;
   public static final int SYSTEM_METRIC = 1;
   public static final int SYSTEM_IMPERIAL = 2;
   public static final int SYSTEM_DISPLAY = 3;
   public static final int SYSTEM_DISPLAY_IMPERIAL = 4;

   public static final int DEFAULT_SYSTEM = SYSTEM_METRIC;

   private static int displaySystem = SYSTEM_DISPLAY;

   private static DoubleFormula[][] TEMPERATURE = {
         {
               new DoubleFactorFormula(1),
               new KelvinToCelsius(),
               new KelvinToFahrenheit(),
               new KelvinToCelsius(),
               new KelvinToFahrenheit()
         },
         {
               new CelsiusToKelvin(),
               new DoubleFactorFormula(1),
               new CelsiusToFahrenheit(),
               new DoubleFactorFormula(1),
               new CelsiusToFahrenheit()
         },
         {
               new FahrenheitToKelvin(),
               new FahrenheitToCelsius(),
               new DoubleFactorFormula(1),
               new FahrenheitToCelsius(),
               new DoubleFactorFormula(1)
         },
         {
               new CelsiusToKelvin(),
               new DoubleFactorFormula(1),
               new CelsiusToFahrenheit(),
               new DoubleFactorFormula(1),
               new CelsiusToFahrenheit()
         },
         {
               new FahrenheitToKelvin(),
               new FahrenheitToCelsius(),
               new DoubleFactorFormula(1),
               new FahrenheitToCelsius(),
               new DoubleFactorFormula(1)
         }};

   private static DoubleFormula[][] TIME = {
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1.0/60),
               new DoubleFactorFormula(1.0/60),
               new DoubleFactorFormula(1.0/60),
               new DoubleFactorFormula(1.0/60)
         },
         {
               new DoubleFactorFormula(60),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(60),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(60),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(60),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         }};

   private static DoubleFormula[][] ALTITUDE = {
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(3.281),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(3.281)
         },
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(3.281),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(3.281)
         },
         {
               new DoubleFactorFormula(0.3048),
               new DoubleFactorFormula(0.3048),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(0.3048),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(3.281),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(3.281)
         },
         {
               new DoubleFactorFormula(0.3048),
               new DoubleFactorFormula(0.3048),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(0.3048),
               new DoubleFactorFormula(1)
         }};

   private static DoubleFormula[][] PRESSURE = {
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(0.00001),
               new DoubleFactorFormula(0.00001*14.5),
               new DoubleFactorFormula(0.00001),
               new DoubleFactorFormula(0.00001*14.5)
         },
         {
               new DoubleFactorFormula(100000),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(14.5),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(14.5)
         },
         {
               new DoubleFactorFormula(100000/14.5),
               new DoubleFactorFormula(1/14.5),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1/14.5),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(100000),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(14.5),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(14.5)
         },
         {
               new DoubleFactorFormula(100000/14.5),
               new DoubleFactorFormula(1/14.5),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1/14.5),
               new DoubleFactorFormula(1)
         }};

   private static DoubleFormula[][] VOLUME = {
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1000),
               new DoubleFactorFormula(35.31)
         },
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1000),
               new DoubleFactorFormula(35.31)
         },
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(35.31),
               new DoubleFactorFormula(1000)
         },
         {
               new DoubleFactorFormula(0.001),
               new DoubleFactorFormula(0.001),
               new DoubleFactorFormula(0.001),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(0.03531)
         },
         {
               new DoubleFactorFormula(0.02832),
               new DoubleFactorFormula(0.02832),
               new DoubleFactorFormula(0.02832),
               new DoubleFactorFormula(28.32),
               new DoubleFactorFormula(1),
         }};
   private static DoubleFormula[][] WEIGHT = {
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         },
         {
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1),
               new DoubleFactorFormula(1)
         }};

   private static DoubleFormula[][] AMV = {
         {
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0/60.0),
               new DoubleFactorFormula(1000.0/60.0),
               new DoubleFactorFormula(1000.0/60.0),
               new DoubleFactorFormula(1000.0/60.0)
         },
         {
               new DoubleFactorFormula(1.0*60.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1000.0),
               new DoubleFactorFormula(1000.0),
               new DoubleFactorFormula(1000.0)
         },
         {
               new DoubleFactorFormula(60.0/1000.0),
               new DoubleFactorFormula(1.0/1000.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0)
         },
         {
               new DoubleFactorFormula(60.0/1000.0),
               new DoubleFactorFormula(1.0/1000.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0)
         },
         {
               new DoubleFactorFormula(60.0/1000.0),
               new DoubleFactorFormula(1.0/1000.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0)
         }};

   private static DoubleFormula[][] SPEED = {
         {
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0/60.0),
               new DoubleFactorFormula(1.0/60.0),
               new DoubleFactorFormula(1.0/60.0),
               new DoubleFactorFormula(1.0/60.0)
         },
         {
               new DoubleFactorFormula(1.0*60.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0)
         },
         {
               new DoubleFactorFormula(1.0*60.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
         },
         {
               new DoubleFactorFormula(1.0*60.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0)
         },
         {
               new DoubleFactorFormula(1.0*60.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0),
               new DoubleFactorFormula(1.0)
         }};

   private int fromSystem;

   private int toSystem;

   public UnitConverter(int fromSystem, int toSystem) {
      this.fromSystem = fromSystem;
      this.toSystem = toSystem;
   }

   public Double convertTemperature(Double d) {
      return TEMPERATURE[this.fromSystem][this.toSystem].convert(d);
   }

   public Double convertTime(Double d) {
      return TIME[this.fromSystem][this.toSystem].convert(d);
   }

   public Double convertAltitude(Double d) {
      return ALTITUDE[this.fromSystem][this.toSystem].convert(d);
   }

   public Double convertPressure(Double d) {
      return PRESSURE[this.fromSystem][this.toSystem].convert(d);
   }

   public Double convertVolume(Double d) {
      return VOLUME[this.fromSystem][this.toSystem].convert(d);
   }

   public Double convertWeight(Double d) {
      return WEIGHT[this.fromSystem][this.toSystem].convert(d);
   }
   public Double convertAMV(Double d) {
      return AMV[this.fromSystem][this.toSystem].convert(d);
   }

   public Double convertSPEED(Double d) {
      return SPEED[this.fromSystem][this.toSystem].convert(d);
   }

   public String formatTemperature(Double d, String format) {
      DecimalFormat f = new DecimalFormat(format);
      return f.format(convertTemperature(d).doubleValue());
   }

   public String formatTime(Double d, String format) {
      DecimalFormat f = new DecimalFormat(format);
      return f.format(convertTime(d).doubleValue());
   }

   public String formatAltitude(Double d, String format) {
      DecimalFormat f = new DecimalFormat(format);
      return f.format(convertAltitude(d).doubleValue());
   }

   public String formatVolume(Double d, String format) {
      DecimalFormat f = new DecimalFormat(format);
      return f.format(convertVolume(d).doubleValue());
   }

   public static String getDisplayWeightUnit() {
      return WEIGHT_UNITS[getDisplaySystem()];
   }

   public static String getDisplayAMVUnit() {
      return AMV_UNITS[getDisplaySystem()];
   }

   public static String getDisplaySpeedUnit() {
      return SPEED_UNITS[getDisplaySystem()];
   }

   public static String getDisplayTemperatureUnit() {
      return TEMPERATURE_UNITS[getDisplaySystem()];
   }

   public static String getDisplayTimeUnit() {
      return TIME_UNITS[getDisplaySystem()];
   }

   public static String getDisplayAltitudeUnit() {
      return ALTITUDE_UNITS[getDisplaySystem()];
   }

   public static String getDisplayPressureUnit() {
      return PRESSURE_UNITS[getDisplaySystem()];
   }

   public static String getDisplayVolumeUnit() {
      return VOLUME_UNITS[getDisplaySystem()];
   }

   public static String getSystemString(int units) {
      if (units <0 || units>=SYSTEMS.length) {
         units = DEFAULT_SYSTEM;
      }
      return SYSTEMS[units];
   }

   public static int getSystem(String units) {
      for (int i=0; i<SYSTEMS.length; i++) {
         if (SYSTEMS[i].equalsIgnoreCase(units)) {
            return i;
         }
      }
      return DEFAULT_SYSTEM;
   }

   public static int getDisplaySystem() {
      return displaySystem;
   }

   private static interface DoubleFormula {
      public Double convert(Double d);
   }

   private static class DoubleFactorFormula implements DoubleFormula {
      double factor;
      public DoubleFactorFormula(double factor) {
         this.factor = factor;
      }
      public Double convert(Double d) {
         //return d==null?null:new Double(d.doubleValue()*factor);
         return d==null?null:Double.valueOf(d.doubleValue()*factor);
      }
   }

   private static class KelvinToCelsius implements DoubleFormula {
      public Double convert(Double d) {
         //return d==null?null:new Double(d.doubleValue()-273.15);
         return d==null?null:Double.valueOf(d.doubleValue()-273.15);
      }
   }

   private static class KelvinToFahrenheit implements DoubleFormula {
      public Double convert(Double d) {
         //return d==null?null:new Double(1.80 * (d.doubleValue() - 273.15) + 32);
         return d==null?null:Double.valueOf(1.80 * (d.doubleValue() - 273.15) + 32);
      }
   }

   private static class CelsiusToFahrenheit implements DoubleFormula {
      public Double convert(Double d) {
         //return d==null?null:new Double(9.0/5.0*d.doubleValue()+32);
         return d==null?null:Double.valueOf(9.0/5.0*d.doubleValue()+32);
      }
   }

   private static class CelsiusToKelvin implements DoubleFormula {
      public Double convert(Double d) {
         //return d==null?null:new Double(d.doubleValue()+273.15);
         return d==null?null:Double.valueOf(d.doubleValue()+273.15);
      }
   }

   private static class FahrenheitToKelvin implements DoubleFormula {
      public Double convert(Double d) {
         //return d==null?null:new Double((d.doubleValue()-32)/1.8+273.15);
         return d==null?null:Double.valueOf((d.doubleValue()-32)/1.8+273.15);
      }
   }

   private static class FahrenheitToCelsius implements DoubleFormula {
      public Double convert(Double d) {
         //return d==null?null:new Double((d.doubleValue()-32.0)*5.0/9.0);
         return d==null?null:Double.valueOf((d.doubleValue()-32.0)*5.0/9.0);
      }
   }

   public static UnitConverter newInstance(int fromSystem, int toSystem) {
      return new UnitConverter(fromSystem, toSystem);
   }

}