import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ashkanmehrkar on 12/9/16.
 */
public class Trie extends Tree {

    private class TrieNode extends Tree.Node {

        LinkedList names = new LinkedList();
        String s;
        boolean flag;
        TrieNode[] children;
        static final int MAX = 46;

        private TrieNode() {

            s = null;
            flag = false;
            children = new TrieNode[46];

        }

    }

    TrieNode root;
    TrieNode searchedWord;

    public Trie() {

        root = new TrieNode();
        words = new ArrayList<String>();
        filesInFolder = new ArrayList<String>();
        currentFiles = new ArrayList<String >();
        searchedWord = new TrieNode();

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
                if (illegal != null && !(illegal.search(s))) {
                    add(root, s, s, file);
                }
                if (illegal == null)
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


    public void add(TrieNode trieNode, String s, String save, File file) {

        if (trieNode == null)
            return;

        int a = (int) s.charAt(0) - (int)('a');

        if (s.charAt(0) == '\'') { a = 26; }
        else if (s.charAt(0) == ',') { a = 27; }
        else if (s.charAt(0) == '?') { a = 28; }
        else if (s.charAt(0) == '!') { a = 29; }
        else if (s.charAt(0) == '.') { a = 30; }
        else if (s.charAt(0) == ':') { a = 31; }
        else if (s.charAt(0) == ';') { a = 32; }
        else if (s.charAt(0) == '\"') { a = 33; }
        else if (s.charAt(0) == ']') { a = 34; }
        else if (s.charAt(0) == '[') { a = 35; }
        else if (s.charAt(0) == '{') { a = 36; }
        else if (s.charAt(0) == '}') { a = 37; }
        else if (s.charAt(0) == '-') { a = 38; }
        else if (s.charAt(0) == '(') { a = 39; }
        else if (s.charAt(0) == ')') { a = 40; }
        else if (s.charAt(0) == '&') { a = 41; }
        else if (s.charAt(0) == '$') { a = 42; }
        else if (s.charAt(0) == '@') { a = 43; }
        else if (s.charAt(0) == '\\') { a = 44; }
        else if(s.charAt(0) == '—') { a = 45;}




        if(trieNode.children[a] == null)
            trieNode.children[a] = new TrieNode();
        if(s.length() == 1) {
            trieNode.children[a].s = save;
            trieNode.children[a].flag = true;
            trieNode.children[a].names.add(file);
            return;
        }
        else
            add(trieNode.children[a], s.substring(1), save, file);
    }

    @Override
    public boolean search(String s) {

        s = s.toLowerCase();
        searchedWord = new TrieNode();
        return search(root, s);

    }
    public boolean search(TrieNode trieNode, String s) {

        if(trieNode == null)
            return false;
        int a = (int)s.charAt(0) - (int)('a');
        if (s.charAt(0) == '\'') { a = 26; }
        else if (s.charAt(0) == ',') { a = 27; }
        else if (s.charAt(0) == '?') { a = 28; }
        else if (s.charAt(0) == '!') { a = 29; }
        else if (s.charAt(0) == '.') { a = 30; }
        else if (s.charAt(0) == ':') { a = 31; }
        else if (s.charAt(0) == ';') { a = 32; }
        else if (s.charAt(0) == '\"') { a = 33; }
        else if (s.charAt(0) == ']') { a = 34; }
        else if (s.charAt(0) == '[') { a = 35; }
        else if (s.charAt(0) == '{') { a = 36; }
        else if (s.charAt(0) == '}') { a = 37; }
        else if (s.charAt(0) == '-') { a = 38; }
        else if (s.charAt(0) == '(') { a = 39; }
        else if (s.charAt(0) == ')') { a = 40; }
        else if (s.charAt(0) == '&') { a = 41; }
        else if (s.charAt(0) == '$') { a = 42; }
        else if (s.charAt(0) == '@') { a = 43; }
        else if (s.charAt(0) == '@') { a = 43; }
        else if (s.charAt(0) == '\\') { a = 44; }
        else if(s.charAt(0) == '—') { a = 45;}

        if (s.length() == 1) {
            if (trieNode.children[a] != null && trieNode.children[a].flag && !trieNode.children[a].names.isEmpty()) {
                searchedWord = trieNode.children[a];
                return true;
            }
            else
                return false;
        }

        return search(trieNode.children[a], s.substring(1));

    }


    @Override
    public void delete(File file) {

        for(int i = 0; i < currentFiles.size(); i++) {
            if(currentFiles.get(i).equals(file.getName()))
                currentFiles.remove(i);
        }

        delete(root, file);

    }
    public void delete(TrieNode trieNode, File file) {

        if (trieNode == null)
            return;
        if (trieNode.flag)
            trieNode.names.delete(file);
        for (int i = 0; i < TrieNode.MAX; i++)
            if (trieNode.children[i] != null)
                delete(trieNode.children[i], file);

    }


    @Override
    public void update(File file, Tree illegal) {

        delete(file);
        add(file, illegal);

    }


    public int wordsNumber() {

        return wordsNumber(root);

    }
    public int wordsNumber(TrieNode trieNode) {

        if (trieNode == null)
            return 0;

        int sum = 0;
        for (int i = 0; i < TrieNode.MAX; i++)
            sum += wordsNumber(trieNode.children[i]);

        if (trieNode.flag)
            return 1 + sum;

        return sum;
    }


    @Override
    public void wordsList() {

        wordsList(root);

    }
    public void wordsList(TrieNode trieNode) {

        if(trieNode == null)
            return;
        for(int i = 0; i < TrieNode.MAX; i++)
            wordsList(trieNode.children[i]);
        if(trieNode.flag && !trieNode.names.isEmpty())
            words.add(trieNode.s + " : " + trieNode.names.getFileNames());

    }


    public void print() {
        triePrint(root);

    }
    public void triePrint(TrieNode trieNode) {
        if (trieNode == null)
            return;
        if(trieNode.flag) {
            System.out.println(trieNode.s);
        }
        for(int i = 0; i < 27; i++) {
            if(trieNode.children[i] != null)
                triePrint(trieNode.children[i]);
        }
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
