package au.org.paperminer.ws;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.smu.tspell.wordnet.*;

public class wordNet {

	/**
	 * @param args
	 * @return 
	 * @throws IOException
	 */
	public  List<String> getWordNet(String word) throws IOException {

		/*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a word: ");
		String word = br.readLine();*/

		// -Dwordnet.database.dir=\\users\amirhosseinkamel\Desktop\WordNet-3.0\dict\
		System.setProperty("wordnet.database.dir", "//users/amirhosseinkamel/Desktop/WordNet-3.0/dict/");
		 List<String> syn = new ArrayList<String>();
		NounSynset nounSynset;
		NounSynset[] hyponyms;

		WordNetDatabase database = WordNetDatabase.getFileInstance();

		Synset[] synsets = database.getSynsets(word, SynsetType.NOUN);

		for (int i = 0; i < synsets.length; i++) {
			nounSynset = (NounSynset) (synsets[i]);
			hyponyms = nounSynset.getHyponyms();
			//nounSynset.
			NounSynset[] hypernymSynset = nounSynset.getHypernyms();
			String[] synonyms = nounSynset.getWordForms();

			for (NounSynset hyperSynset : hypernymSynset) {
				String[] hypernyms = hyperSynset.getWordForms();
				//System.out.print("hypernyms: ");
				for (int k = 0; k < hypernyms.length; k++) {
					syn.add(hypernyms[k]);
					//System.out.print(hypernyms[k]+"- ");

					//System.out.print(hypernyms[k]+"- ");
				}
				//System.out.println();

			}
			
			//for (NounSynset synonymsSynset : synonyms) {
				
				//System.out.print("synonyms: ");
				for (int k = 0; k < synonyms.length; k++) {
					syn.add(synonyms[k]);
					//System.out.print(synonyms[k]+"- ");

					//System.out.print(hypernyms[k]+"- ");
				}
				//System.out.println();

			
		}
		return syn;
		
	}
}
