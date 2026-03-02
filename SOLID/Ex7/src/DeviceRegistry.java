import java.util.*;

public class DeviceRegistry {
    private final List<Object> devices = new ArrayList<>();

    public void add(Object device) { 
        devices.add(device); 
    }

    public <T> T getFirstOfType(Class<T> capability) {
        for (Object device : devices) {
            if (capability.isInstance(device)) {
                return capability.cast(device);
            }
        }
        throw new IllegalStateException("No device with capability: " + capability.getSimpleName());
    }
}