import java.util.Comparator;

/**
 * 游客比较器：实现Comparator接口，定义排序规则
 * 排序逻辑：先按年龄升序，再按会员类型降序（VIP > 普通会员 > 临时游客）
 */
public class VisitorComparator implements Comparator<Visitor> {
    @Override
    public int compare(Visitor v1, Visitor v2) {
        // 第一步：按年龄升序排序（核心比较字段1）
        int ageCompare = Integer.compare(v1.getAge(), v2.getAge());
        if (ageCompare != 0) {
            return ageCompare;
        }

        // 第二步：年龄相同时，按会员类型降序排序（核心比较字段2）
        // 定义会员类型权重：VIP=3，普通会员=2，临时游客=1
        int weight1 = getMembershipWeight(v1.getMembershipType());
        int weight2 = getMembershipWeight(v2.getMembershipType());
        return Integer.compare(weight2, weight1); // 降序：权重高的在前
    }

    /**
     * 辅助方法：给会员类型分配权重
     */
    private int getMembershipWeight(String membershipType) {
        return switch (membershipType.trim()) {
            case "VIP" -> 3;
            case "普通会员" -> 2;
            case "临时游客" -> 1;
            default -> 0; // 未知类型权重最低
        };
    }
}