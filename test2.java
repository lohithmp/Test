    import java.lang.reflect.Field;

public class MyDTO {
    private int a;
    private int b;
    private String name;
    private int age;

    // Constructor (optional)
    public MyDTO() {}

    // Setters for each field (or you could use reflection to set these dynamically)
    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // ToString for printing the object
    @Override
    public String toString() {
        return "MyDTO{" +
                "a=" + a +
                ", b=" + b +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}



import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class KeyValueExample {
    public static void main(String[] args) {
        // Example data (Array of Maps with dynamic keys)
        Map<String, Object>[] data = new Map[]{
            Map.of("a", 0, "b", 1),
            Map.of("name", "Alice", "age", 25),
            Map.of("a", 10, "b", 20, "name", "Bob", "age", 30)
        };

        // Transform the array of maps into a list of MyDTO objects using reflection
        List<MyDTO> myDTOList = Arrays.stream(data)
            .map(map -> {
                MyDTO dto = new MyDTO();
                // Iterate through the map and set fields dynamically
                map.forEach((key, value) -> {
                    try {
                        // Get the field by name
                        Field field = MyDTO.class.getDeclaredField(key);
                        field.setAccessible(true); // Allow access to private fields
                        field.set(dto, value); // Set the value from the map
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace(); // Handle cases where the field does not exist
                    }
                });
                return dto;
            })
            .collect(Collectors.toList());

        // Print out the transformed DTOs
        myDTOList.forEach(System.out::println);
    }
}





MyDTO{a=0, b=1, name='null', age=0}
MyDTO{a=0, b=0, name='Alice', age=25}
MyDTO{a=10, b=20, name='Bob', age=30}
