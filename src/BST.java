import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ashkanmehrkar on 12/8/16.
 */
public class BST extends Tree {

    class BSTNode extends Tree.Node {
        BSTNode lc;
        BSTNode rc;
        String s = "";
        LinkedList names = new LinkedList();
    }

    BSTNode root;
    BSTNode searchedWord;

    public BST() {
        root = new BSTNode();
        words = new ArrayList<String>();
        currentFiles = new ArrayList<String>();
        filesInFolder = new ArrayList<String>();
        searchedWord = new BSTNode();
    }

    @Override
    public void add(File file, Tree illegal){
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc2.hasNextLine()) {
            Scanner s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()) {
                String s = s2.next();
                s = s.toLowerCase();
                if (illegal != null && !(illegal.search(s))) {
                    add(root, s, file);
                }
                if (illegal == null)
                    add(root, s, file);
            }
        }
        boolean exist = false;
        for(String s : filesInFolder) {
            if(s.equals(file.getName()))
                exist = true;
        }
        if(!exist) {
            filesInFolder.add(file.getName());
        }
        currentFiles.add(file.getName());
    }
    public void add(BSTNode father, String s, File file) {
        if(father.s.equals(s)) {
            father.names.add(file);
            return;
        }
        if (father.s.equals("")) {
            //System.out.println(s);
            father.s = s;
            father.names.add(file);
            return;
        }
        for(int i = 0; i < Integer.min(s.length(), father.s.length()); i++) {
            if(s.charAt(i) > father.s.charAt(i)) {
                if(father.rc == null) {
                    father.rc = new BSTNode();
                    //System.out.println(s);
                    father.rc.s = s;
                    father.rc.names.add(file);
                    return;
                }
                else {
                    add(father.rc, s, file);
                    return;
                }
            }
            else if(s.charAt(i) < father.s.charAt(i)) {
                if(father.lc == null) {
                    father.lc = new BSTNode();
                    //System.out.println(s);
                    father.lc.s = s;
                    father.lc.names.add(file);
                    return;
                }
                else {
                    add(father.lc, s, file);
                    return;
                }
            }
        }

        if(s.length() > father.s.length()) {
            if(father.rc == null) {
                father.rc =  new BSTNode();
                //System.out.println(s);
                father.rc.s = s;
                //System.out.println(file.getName());
                father.rc.names.add(file);
            }
            else {
                add(father.rc, s, file);
                return;
            }
        }
        else if(s.length() < father.s.length()) {
            if(father.lc == null) {
                father.lc = new BSTNode();
                //System.out.println(s);
                father.lc.s = s;
                father.lc.names.add(file);
            }
            else {
                add(father.lc, s, file);
                return;
            }
        }
    }


    @Override
    public boolean search(String s) {
        s = s.toLowerCase();
        searchedWord = new BSTNode();
        return search(root, s);
    }
    public boolean search(BSTNode bstNode,String s) {
        if(bstNode == null)
            return false;
        if(bstNode.s.equals(s)) {
            searchedWord = bstNode;
            return !(bstNode.names.isEmpty());
        }
        return search(bstNode.lc, s) || search(bstNode.rc, s);
    }


    @Override
    public void delete(File file) {
        for(int i = 0; i < currentFiles.size(); i++) {
            if(currentFiles.get(i).equals(file.getName()))
                currentFiles.remove(i);
        }
        delete(root, file);
    }
    public void delete(BSTNode bstNode, File file) {
        if(bstNode == null)
            return;
        bstNode.names.delete(file);
        delete(bstNode.lc, file);
        delete(bstNode.rc, file);
    }


    @Override
    public void update(File file, Tree illegal) {
        delete(file);
        add(file, illegal);
    }


    @Override
    public void wordsList() {
        wordsList(root);
    }
    public void wordsList(BSTNode bstNode) {
        if(bstNode == null)
            return;
        if(!bstNode.names.isEmpty()) {
            words.add(bstNode.s + " : " + bstNode.names.getFileNames());
        }
        wordsList(bstNode.lc);
        wordsList(bstNode.rc);
    }


    @Override
    public void print() {
        print(root);
    }

    public void print(BSTNode bst) {
        if (bst == null)
            return;
        if(!bst.names.isEmpty()) {
            System.out.print("( " + bst.s + "/ ");
            if(bst.s.equals("york"))
                System.out.println("hello");
            bst.names.print();
        }
        if(bst.lc != null)
            print(bst.lc);
        if(bst.rc !=null)
            print(bst.rc);
        System.out.print(" )");
    }


    int wordsNumber() {
        return wordsNumber(root);
    }
    int wordsNumber(BSTNode bstNode) {
        if(bstNode == null)
            return 0;
        else{
            if(bstNode.names.isEmpty())
                return wordsNumber(bstNode.lc) + wordsNumber(bstNode.rc);
            else
                return 1 + wordsNumber(bstNode.lc) + wordsNumber(bstNode.rc);
        }
    }


    String findWord(String s) {
        search(s);
        return searchedWord.names.getFileNames();
    }


    String findSentence(String s) {
        String strings[] = s.split(" ");
        for(String q : strings)
            System.out.println(q);
        ArrayList<String>[] files = new ArrayList[strings.length];
        ArrayList<String> result = new ArrayList<>();
        String tmp = "";
        for(int i = 0; i < strings.length; i++) {
            search(strings[i]);
            files[i] = searchedWord.names.getFiles();
        }
        boolean tmp1 = true;
        for(int i = 0; i < files[0].size(); i++) {
            for(int j = 1; j < files.length; j++) {
                tmp1 = tmp1 && searchFile(files[0].get(i), files[j]);
            }
            if(tmp1)
                result.add(files[0].get(i));
            tmp1 = true;
        }

        for(int i = 0; i < result.size(); i++)
            tmp = tmp.concat(result.get(i) + "\n");
        return tmp;
    }


    private boolean searchFile(String s, ArrayList<String> arrayList) {

        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).equals(s))
                return true;
        }
        return false;

    }

}
