import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.Highlighter.HighlightPainter;
import java.util.Scanner;
import java.io.*;
import java.awt.event.*;
public class Runner
{
    public static JPanel panel = new JPanel();
    public static JFrame mainFrame = new JFrame("OctoEditor");
    public static JTextArea mainFrameArea = new JTextArea(20, 30);
    public static final Highlighter h = mainFrameArea.getHighlighter();
    public static final HighlightPainter r = new DefaultHighlighter.DefaultHighlightPainter(Color.red);//initializes the different highlighter colors
    public static final HighlightPainter g = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
    public static final HighlightPainter b = new DefaultHighlighter.DefaultHighlightPainter(Color.blue);
    public static final HighlightPainter y = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
    public static final HighlightPainter o = new DefaultHighlighter.DefaultHighlightPainter(Color.orange);
    public static ArrayList<String> dictionaryWords = new ArrayList<>();
    public static ArrayList<String> prepositions = new ArrayList<>();
    public static ArrayList<String> contractions = new ArrayList<>();
    public static ArrayList<String> adverbs = new ArrayList<>();
    public static ArrayList<String> irregularPastVerbsList = new ArrayList<>();
    public static int index = 0;//location the reader is at
    public static int previousWordLengthWithSpaces = 0;
    public static void main(String[] args) throws FileNotFoundException, BadLocationException
    {
        JTextArea keyArea = new JTextArea("misspelling\ncapitalization\nending sentence on preposition\nadverb\ncontraction\npassive voice\nThank you for testing out OctoEditor.\nThis program is in an early alpha and any feedback is greatly appreciated.\nYou can leave feedback at:\nhttps://docs.google.com/forms/d/11YPf6VuYaQvfDNbEOmvQtVoKjBTee2KjuLjYyUm-ors/viewform?usp=send_form");
        Highlighter legendHighlighter = keyArea.getHighlighter();
        legendHighlighter.addHighlight(0, 11, r);
        legendHighlighter.addHighlight(12, 13, r);
        legendHighlighter.addHighlight(26, 57, g);
        legendHighlighter.addHighlight(57, 64, y);
        legendHighlighter.addHighlight(64, 76, b);
        legendHighlighter.addHighlight(77, 90, o);
        keyArea.setEditable(false);
        ImageIcon octoEditorIcon = new ImageIcon("/C:/Program Files (x86)/OctoEditor/10492536_301698863338193_2381679877369749008_n.jpg");
        JFrame key = new JFrame("Guide");
        key.setIconImage(octoEditorIcon.getImage());
        key.setLayout(new BorderLayout());
        key.add(keyArea, BorderLayout.CENTER);
        key.setSize(650, 300);
        key.setVisible(true);
        File dictionaryFile = new File("/C:/Program Files (x86)/OctoEditor/dictionary2.txt");
        Scanner fileReader = new Scanner(dictionaryFile);//puts words into arraylists
        while(fileReader.hasNext())
        {
            dictionaryWords.add(fileReader.next());
        }
        File prepositionFile = new File("/C:/Program Files (x86)/OctoEditor/prepositions.txt");
        fileReader = new Scanner(prepositionFile);
        while(fileReader.hasNext())
        {
            prepositions.add(fileReader.next());
        }
        File contractionFile = new File("/C:/Program Files (x86)/OctoEditor/contractions.txt");
        fileReader = new Scanner(contractionFile);
        while(fileReader.hasNext())
        {
            contractions.add(fileReader.next());
        }
        File adverbFile = new File("/C:/Program Files (x86)/OctoEditor/adverbs.txt");
        fileReader = new Scanner(adverbFile);
        while(fileReader.hasNext())
        {
            adverbs.add(fileReader.next());
        }
        File irregularPastVerbsFile = new File("/C:/Program Files (x86)/OctoEditor/irregular past tense verbs.txt");
        fileReader = new Scanner(irregularPastVerbsFile);
        while(fileReader.hasNext())
        {
            irregularPastVerbsList.add(fileReader.next());
        }
        mainFrameArea.setLineWrap(true);
        panel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(mainFrameArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);
        JButton button = new JButton("Edit");
        panel.add(button, BorderLayout.NORTH);
        class ClickListener implements ActionListener 
        {
            public void actionPerformed(ActionEvent e)
            {
                Highlighter.Highlight[] hilites = h.getHighlights();
                for (int i = 0; i < hilites.length; i++)
                {
                    if (hilites[i].getPainter() instanceof Highlighter.HighlightPainter)
                    {
                        h.removeHighlight(hilites[i]);
                    }
                }
                Scanner in = new Scanner(mainFrameArea.getText());//reads in words
                Scanner spacesReader = new Scanner(mainFrameArea.getText());//reads in spaces
                in.useDelimiter("[-— \n\t]+");
                spacesReader.useDelimiter("[^-— \n\t]+");
                String previousWord = "";
                //previousWordLengthWithSpaces = 0;
                while(in.hasNext())//loop that iterates until all words from main frame TextArea are read and processed
                {
                    String checkWord = in.next();
                    String spacesString = "";
                    if(spacesReader.hasNext())
                    {
                        spacesString = spacesReader.next();
                    }
                    try
                    {
                        if(previousWord.equalsIgnoreCase("a"))
                        {
                            String checkWordFirstLetter = checkWord.substring(0, 1);
                            if(checkWordFirstLetter.equalsIgnoreCase("a") || checkWordFirstLetter.equalsIgnoreCase("e") || checkWordFirstLetter.equalsIgnoreCase("i") || checkWordFirstLetter.equalsIgnoreCase("o") || checkWordFirstLetter.equalsIgnoreCase("u"))
                            {
                                h.addHighlight(index - previousWordLengthWithSpaces, index - previousWordLengthWithSpaces + previousWord.length(), r);//highlights incorrect use of a
                            }
                        }
                        if(previousWord.equalsIgnoreCase("an"))
                        {
                            String checkWordFirstLetter = checkWord.substring(0, 1);
                            if(!(checkWordFirstLetter.equalsIgnoreCase("a") || checkWordFirstLetter.equalsIgnoreCase("e") || checkWordFirstLetter.equalsIgnoreCase("i") || checkWordFirstLetter.equalsIgnoreCase("o") || checkWordFirstLetter.equalsIgnoreCase("u")))
                            {
                                h.addHighlight(index - previousWordLengthWithSpaces, index - previousWordLengthWithSpaces + previousWord.length(), r);//highlights incorrect use of an
                            }
                        }
                        prepositionCheck(checkWord);
                        contractionCheck(removePunctuation(checkWord));
                        adverbCheck(removePunctuation(checkWord));
                        passiveCheck(removePunctuation(checkWord), previousWord);
                        if(previousWord.equals("") || previousWord.substring(previousWord.length()-1, previousWord.length()).equals(".") || previousWord.substring(previousWord.length()-1, previousWord.length()).equals("?") || previousWord.substring(previousWord.length()-1, previousWord.length()).equals("!"))
                        {
                            spellCheck(removePunctuation(checkWord), previousWord);
                            capitalizationCheck(removePunctuation(checkWord));
                        }
                        else
                        {
                            spellCheck(removePunctuation(checkWord), previousWord);
                        }
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    previousWord = checkWord;
                    previousWordLengthWithSpaces = checkWord.length() + spacesString.length();//length of previous word and previous spaces
                    index = index + previousWordLengthWithSpaces;//counts the index in the JTextArea by adding length of scanned word and adds one for space
                }
                index = 0;
            }
        }
        ActionListener listener = new ClickListener();
        button.addActionListener(listener);
        mainFrame.add(panel);
        mainFrame.setIconImage(octoEditorIcon.getImage());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 400);
        mainFrame.setVisible(true);
    }

    public static String removePunctuation(String w)
    {
        if(w.length()>1)
        {
            w = w.replace(".", "").replace(",", "").replace("(", "").replace("\"", "").replace(";", "").replace(":", "").replace(")", "").replace("!", "").replace("?", "").replace("—", "").replace("~", "");
            return w.substring(0,1).replace("'", "").replace("`", "") + w.substring(1,w.length() - 1) + w.substring(w.length()- 1, w.length()).replace("'", "").replace("`", "");
        }
        else
        {
            return w;
        }
    }

    public static void spellCheck(String w, String previousW) throws FileNotFoundException, BadLocationException
    {
        if(previousW.equals("") || previousW.contains(".") || previousW.contains("?") || previousW.contains("!"))
        {
            for(int i = 0; i < dictionaryWords.size(); i++)
            {
                if(w.equalsIgnoreCase(dictionaryWords.get(i)))
                {
                    return;
                }
            }
        }
        else
        {
            if(!previousW.equals(w))
            {
                for(int i = 0; i < dictionaryWords.size(); i++)
                {
                    if(w.equals(dictionaryWords.get(i)))
                    {
                        return;
                    }
                }
            }
        }
        h.addHighlight(index, index + w.length(), r);
    }

    public static int caseMatches(String w, String properCapitalizationW)//0 = not matching 1 = matching word and case 2 = matching word but not case | if 0 highlight whole word if 1 highlight nothing if 2 highlight first letter
    {
        if(w.equals(properCapitalizationW))
        {
            return 1;
        }
        else if(w.equalsIgnoreCase(properCapitalizationW))
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }

    public static void capitalizationCheck(String w) throws BadLocationException
    {
        if(!Character.isUpperCase(w.charAt(0)))
        {
            h.addHighlight(index, index + 1, r);//tells TextArea to highlight word if it isn't capitalized properly
        }
    }

    public static void prepositionCheck(String w) throws BadLocationException
    {
        if(w.contains(".") || w.contains("?") || w.contains("!"))
        {
            w = removePunctuation(w);
            for(int i = 0; i < prepositions.size(); i++)
            {
                if(w.equalsIgnoreCase(prepositions.get(i)))
                {
                    h.addHighlight(index, index + w.length(), g);//tells TextArea to highlight word if it preposition at end of sentence
                }
            }
        }
    }

    public static void contractionCheck(String w) throws BadLocationException
    {
        if(w.contains("'"))
        {
            for(int i = 0; i < contractions.size(); i++)
            {
                if(w.equalsIgnoreCase(contractions.get(i)))
                {
                    h.addHighlight(index, index + w.length(), b);//highlights contractions
                }
            }
        }
    }

    public static void adverbCheck(String w) throws BadLocationException
    {
        if(w.toLowerCase().contains("ly"))//temporary check that has many exceptions, but a better check will be added once I get performace up, otherwise it takes too long if you check word against all adverbs
        {
            for(int i = 0; i < adverbs.size(); i++)
            {
                if(w.equalsIgnoreCase(adverbs.get(i)))
                {
                    h.addHighlight(index, index + w.length(), y);//highlights adverbs
                }
            }
        }
    }

    public static void passiveCheck(String w, String previousW) throws BadLocationException
    {
        if(previousW.equalsIgnoreCase("am") || previousW.equalsIgnoreCase("are") || previousW.equalsIgnoreCase("is") || previousW.equalsIgnoreCase("was") || previousW.equalsIgnoreCase("were") || previousW.equalsIgnoreCase("be") || previousW.equalsIgnoreCase("been") || previousW.equalsIgnoreCase("being"))//checks if first word is to be verb
        {
            for(int i = 0; i < irregularPastVerbsList.size(); i++)
            {
                //TODO fix so it does not go out of bounds if word after to be verb is less than 2 in length such as "was a"
                if(w.equalsIgnoreCase(irregularPastVerbsList.get(i)) || isPastParticiple(w))//checks if second word is past participle verb
                {
                    h.addHighlight(index - previousWordLengthWithSpaces, index + w.length(), o);//highlights passive voice
                }
            }
        }
    }

    public static boolean isPastParticiple(String w)
    {
        if(w.length() > 2)
        {
            return w.substring(w.length() - 2, w.length()).equalsIgnoreCase("ed");//checks if contains ed at end
        }
        else
        {
            return false;
        }
    }
}