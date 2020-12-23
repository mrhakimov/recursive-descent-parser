import java.util.Arrays;
import java.util.List;

class Tree {
    private String node;
    private List<Tree> children;

    Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    Tree(String node) {
        this.node = node;
    }

    List<Tree> getChildren() {
        return children;
    }

    String getNode() {
        return node;
    }
}
