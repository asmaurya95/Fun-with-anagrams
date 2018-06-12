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
    private static ArrayList<String> wordList = new ArrayList<>();
    private static HashSet<String> wordSet = new HashSet<>();
    private static HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            String sortedWord = sortLetters(word);
            if(lettersToWord.containsKey(sortedWord)) {
                lettersToWord.get(sortedWord).add(word);
            }
            else {
                lettersToWord.put(sortedWord, new ArrayList<>(Arrays.asList(word)));
            }
            wordSet.add(word);
            wordList.add(word);
        }
        /*for (int i = 0; i < wordList.size(); i++) {
            Log.d("wordList -- ", wordList.get(i));
        }
        Log.d("wordList:SIZE", Integer.toString(wordList.size()));*/
    }

    // Method to check whether a is a substring of b
    public static boolean isSubstring(String a, String b) {
        return b.contains(a);
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !isSubstring(base, word))
            return true;
        return false;
    }

    public static String sortLetters(String word) {
        String res = "";
        int alphabet_count[] = new int[(int)Character.MAX_VALUE];
        for(int i = 0; i < 26; i++) {
            alphabet_count[i] = 0;
        }
        for(int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            alphabet_count[(int)ch-(int)('a')]++;
        }
        for(int i = 0; i < 26; i++) {
            while(alphabet_count[i] > 0) {
                char ch = (char)(i+'a');
                res += ch;
                alphabet_count[i] = alphabet_count[i] - 1;
            }
        }
        return res;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String b = sortLetters(targetWord);
        for(int i = 0; i < wordList.size(); i++) {
            String s = wordList.get(i);
            String a = sortLetters(s);
            if (a.length() == b.length() && a.equals(b))
                result.add(wordList.get(i));
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; i < 26; i++) {
            char ch = (char)(i + 97);
            String key = sortLetters(word + ch);
            if(lettersToWord.containsKey(key)) {
                ArrayList<String> a = lettersToWord.get(key);
                if(wordSet.contains(key))
                    result.add(key);
                result.addAll(a);
            }
        }
        /*for(String str : result) {
            Log.d(word, str);
        }*/
        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
}
