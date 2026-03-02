public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { 
        this.reg = reg; 
    }

    public void startClass() {
        // Projector: needs InputConnectable capability
        InputConnectable projector = reg.getFirstOfType(InputConnectable.class);
        Powerable projectorPower = (Powerable) projector;
        projectorPower.powerOn();
        projector.connectInput("HDMI-1");

        // Lights: needs BrightnessControllable capability
        BrightnessControllable lights = reg.getFirstOfType(BrightnessControllable.class);
        lights.setBrightness(60);

        // AC: needs TemperatureControllable capability
        TemperatureControllable ac = reg.getFirstOfType(TemperatureControllable.class);
        ac.setTemperatureC(24);

        // Scanner: needs AttendanceCapable capability
        AttendanceCapable scanner = reg.getFirstOfType(AttendanceCapable.class);
        System.out.println("Attendance scanned: present=" + scanner.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        
        // Power down all Powerable devices
        Powerable projector = (Powerable) reg.getFirstOfType(InputConnectable.class);
        projector.powerOff();
        
        Powerable lights = (Powerable) reg.getFirstOfType(BrightnessControllable.class);
        lights.powerOff();
        
        Powerable ac = (Powerable) reg.getFirstOfType(TemperatureControllable.class);
        ac.powerOff();
    }
}