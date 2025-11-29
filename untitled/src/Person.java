/**
 * 抽象基类：封装所有人员（员工/游客）的共性属性与行为
 * 不可实例化，仅作为继承父类
 */
public abstract class Person {
    // 共性实例变量（满足至少3个要求）
    private String id;       // 唯一标识（如身份证号）
    private String name;     // 姓名
    private int age;         // 年龄

    // 无参构造器
    public Person() {}

    // 带参构造器（初始化所有共性属性）
    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getter/Setter（封装属性访问）
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        // 数据验证：年龄需为正数
        if (age > 0) {
            this.age = age;
        } else {
            System.out.println("错误：年龄必须为正数");
        }
    }

    // 抽象方法：子类必须实现格式化输出（多态体现）
    @Override
    public abstract String toString();
}