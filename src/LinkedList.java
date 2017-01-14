import java.io.File;
import java.util.ArrayList;

/**
 * Created by ashkanmehrkar on 12/8/16.
 */
public class LinkedList {
    private class Node {
        String fileName;
        Node nextNode;
    }
    Node root;
    public LinkedList() {
        root = new Node();
    }
    void add(File file) {
        if(root.nextNode == null) {
            root.nextNode = new Node();
            root.nextNode.fileName = file.getName();
            return;
        }
        Node tmp = root;
        while(tmp.nextNode != null) {
            if (tmp.nextNode.fileName.equals(file.getName()))
                return;
            tmp = tmp.nextNode;
        }
        tmp.nextNode = new Node();
        tmp.nextNode.fileName = file.getName();
    }
    void delete(File file) {
        Node tmp = root;
        if(root.nextNode == null)
            return;
        while(tmp.nextNode != null) {
            if(tmp.nextNode.fileName.equals(file.getName())) {
                if(tmp.nextNode.nextNode != null) {
                    tmp.nextNode = tmp.nextNode.nextNode;
                    return;
                }
                else {
                    tmp.nextNode = null;
                    return;
                }
            }
            else
                tmp = tmp.nextNode;
        }
    }
    void print() {
        Node n = root;
        while(n.nextNode != null) {
            System.out.print(n.nextNode.fileName + "  ");
            n = n.nextNode;
        }
    }
    boolean isEmpty() {
        if (root.nextNode == null)
            return true;
        return false;
    }

    String getFileNames() {
        String s = "";
        Node n = root;
        while(n.nextNode != null) {
            s = s.concat(n.nextNode.fileName + "\n");
            n = n.nextNode;
        }
        return s;
    }
    ArrayList getFiles() {
        ArrayList<String> arrayList = new ArrayList<>();
        Node n = root;
        while(n.nextNode != null) {
            arrayList.add(n.nextNode.fileName);
            n = n.nextNode;
        }
        return  arrayList;
    }
}
