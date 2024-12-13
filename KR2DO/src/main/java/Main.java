import controller.TreeController;
import model.BinaryTreeMyModel;
import view.TreeView;

// Main.java
public class Main {
    public static void main(String[] args) {
        BinaryTreeMyModel tree = new BinaryTreeMyModel();
        TreeView view = new TreeView();
        TreeController controller = new TreeController(tree, view);
        view.setVisible(true);
    }
}