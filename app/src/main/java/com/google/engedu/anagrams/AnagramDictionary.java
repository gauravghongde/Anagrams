/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    ArrayList<String> wordList = new ArrayList<String>();
    HashSet<String> wordSet = new HashSet<>();
    HashMap<String,ArrayList<String>> letterstoWord = new HashMap<>();
    HashMap<Integer,ArrayList<String>> sizetoWord = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            putinWords(word);
            //sizetoWord.put(word.length(),add)

            ArrayList<String> myList = new ArrayList<String>();
            if(!letterstoWord.containsKey(word))
            {
                myList.add(word);
                letterstoWord.put(sorted(word),myList);
            }
            else
            {
                myList = (ArrayList)letterstoWord.get(sorted(word));
                myList.add(word);
                letterstoWord.put(sorted(word),myList);
            }
        }
    }

    public void putinWords(String word)
    {
        int size = word.length();
        ArrayList<String> myList2 = new ArrayList<String>();
        if(!sizetoWord.containsKey(size))
        {
            myList2.add(word);
            sizetoWord.put(size,myList2);
        }
        else
        {
            myList2 = (ArrayList)sizetoWord.get(size);
            myList2.add(word);
            sizetoWord.put(size,myList2);
        }
    }

    public boolean isGoodWord(String word, String base)
    {
        if (word.contains(base) && !wordSet.contains(word))
            return false;
        else
            return true;
    }

    public List<String> getAnagrams(String targetWord)//returns list
    {
        String wordFromList;
        ArrayList<String> result = new ArrayList<String>();

        for(int i=0; i<wordList.size(); i++)
        {
            wordFromList = wordList.get(i);
            if(isAnagram(targetWord,wordFromList))
                result.add(wordFromList);
            else
                continue;
        }

        return result;
    }

    public boolean isAnagram(String word1,String word2)
    {

        String sortedW1 = sorted(word1);
        String sortedW2 = sorted(word2);

        if(sortedW1.equalsIgnoreCase(sortedW2))
            return true;
        else
            return false;
    }

    public String sorted(String word)
    {
        char[] myArray = word.toCharArray();
        Arrays.sort(myArray);
        return (new String(myArray));
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> temp;
        for(char alpha = 'a'; alpha<='z'; alpha++)
        {
            String newWord = word+alpha;
            String sortedKey = sorted(newWord);
            if(letterstoWord.containsKey(sortedKey))
            {
                temp = new ArrayList<>();
                temp = (ArrayList)letterstoWord.get(sortedKey);
                for(int i=0; i<temp.size(); i++)
                    result.add(String.valueOf(temp.get(i)));
            }

        }
        return result;
    }

    public String pickGoodStarterWord()
    {
        ArrayList<String> temp = new ArrayList<>();
        temp = sizetoWord.get(3);
        /*int j = 0;
        if(temp.get(j)!=null)
            return temp.get(j);
        else
        {
            i++;
        }*/

    return temp.get(1);
    }
}
