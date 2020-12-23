import java.util.ArrayList;
import java.util.Objects;

class TreeEncoder {
    private static int globalId = 1;
    private static String result = "digraph G {\n";
    private static Tree root;

    TreeEncoder(Tree root) {
        this.root = root;
    }

    private void dfs(Tree node, int parentId) {
        result += "\t" + parentId + " [label=\"" + node.getNode() + "\"]\n";

        if (!Objects.isNull(node.getChildren())) {
            for (Tree child : node.getChildren()) {
                if (!Objects.isNull(child)) {
                    result += "\t" + parentId + " -> " + globalId + "\n";

                    dfs(child, globalId++);
                }
            }
        }
    }

    String getResult() {
        dfs(root, globalId++);
        result += "}";
        return result;
    }
}
