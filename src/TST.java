import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ashkanmehrkar on 12/9/16.
 */
public class TST extends Tree {

    class TSTNode extends Tree.Node {
        char c = '-';
        String s;
        boolean flag;
        TSTNode lc;
        TSTNode mc;
        TSTNode rc;
        LinkedList names = new LinkedList();
    }

    TSTNode root;
    TSTNode searchedWord;
    ArrayList<TSTNode> searchedSentence;

    public TST() {
        root = new TSTNode();
        words = new ArrayList<String>();
        filesInFolder = new ArrayList<String>();
        currentFiles = new ArrayList<String>();
        searchedWord = new TSTNode();

    }

    @Override
    public boolean search(String s) {
        s = s.toLowerCase();
        searchedWord = new TSTNode();
        return search(root, s);
    }
    public boolean search(TSTNode tstNode, String s) {
        if(tstNode == null)
            return false;
        if(tstNode.flag && tstNode.s.equals(s) && !tstNode.names.isEmpty()) {
            searchedWord = tstNode;
            return true;
        }
        else
            return search(tstNode.lc, s) || search(tstNode.mc, s) || search(tstNode.rc ,s);
    }


    @Override
    public void add(File file, Tree illegal) {
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
                if(illegal != null && !(illegal.search(s))) {
                    add(root, s, s, file);
                }
                if(illegal == null)
                    add(root, s, s, file);
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
    public void add(TSTNode father, String s,String saved, File file) {
        if(father.c == '-') {
            father.c = s.charAt(0);
            if(s.length() == 1) {
                father.flag = true;
                father.s = saved;
                father.names.add(file);
                //words.add(saved);
                return;
            }
            else {
                father.mc = new TSTNode();
                add(father.mc, s.substring(1), saved, file);
                return;
            }
        }
        if (s.charAt(0) > father.c) {
            if (father.rc == null)
                father.rc = new TSTNode();
            add(father.rc, s, saved, file);
        }
        else if (s.charAt(0) < father.c) {
            if (father.lc == null)
                father.lc = new TSTNode();
            add(father.lc, s, saved, file);
        }
        else if(s.charAt(0) == father.c) {
            if(s.length() == 1) {
                father.flag = true;
                father.s = saved;
                father.names.add(file);
                return;
            }
            else {
                if (father.mc == null)
                    father.mc = new TSTNode();
                add(father.mc, s.substring(1), saved, file);
            }
        }
    }


    @Override
    public void delete(File file) {
        for(int i = 0; i < currentFiles.size(); i++) {
            if(currentFiles.get(i).equals(file.getName()))
                currentFiles.remove(i);
        }
        delete(root, file);
    }
    public void delete(TSTNode tstNode, File file) {
        if(tstNode == null)
            return;
        if(tstNode.flag)
            tstNode.names.delete(file);
        delete(tstNode.lc, file);
        delete(tstNode.mc, file);
        delete(tstNode.rc, file);
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
    public void wordsList(TSTNode tstNode) {
        if(tstNode == null)
            return;
        if(tstNode.flag && !tstNode.names.isEmpty())
            words.add(tstNode.s + " : " + tstNode.names.getFileNames());
        wordsList(tstNode.lc);
        wordsList(tstNode.mc);
        wordsList(tstNode.rc);
    }


    @Override
    public void print() {
        print(root);
    }
    public void print(TSTNode tstNode) {
        if(tstNode == null)
            return;
        if(tstNode.flag && !tstNode.names.isEmpty()) {
                System.out.print(tstNode.s + "/ ");
                tstNode.names.print();
        }
        print(tstNode.lc);
        print(tstNode.mc);
        print(tstNode.rc);
    }


    int wordsNumber() {
        return  wordsNumber(root);
    }
    int wordsNumber(TSTNode tstNode) {
        if(tstNode == null)
            return 0;
        if(tstNode.flag && !tstNode.names.isEmpty()) {
            return wordsNumber(tstNode.lc) + wordsNumber(tstNode.mc) + wordsNumber(tstNode.rc) + 1;
        }
        return wordsNumber(tstNode.lc) + wordsNumber(tstNode.mc) + wordsNumber(tstNode.rc);
    }


    String findWord(String s) {
        search(s);
        return searchedWord.names.getFileNames();
    }


    String findSentence(String s) {
        String strings[] = s.split(" ");
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
