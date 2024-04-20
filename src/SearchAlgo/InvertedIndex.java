package src.SearchAlgo;


import java.util.*;

/*
Certainly! Let's dive into the Inverted Index algorithm and its implementation in the context of an online shopping system.

Explanation:
The Inverted Index is a data structure commonly used in information retrieval systems, including search engines and product search
 functionality in online shopping platforms. It is designed to efficiently map keywords to the documents or products that contain them.

In the context of an online shopping system, the Inverted Index allows for fast and efficient searching of products based on user-provided keywords.
Instead of scanning through all the products to find matches for a given keyword, the Inverted Index pre-processes the
product data and creates a mapping from keywords to the products that contain them.

The Inverted Index consists of two main components:

Vocabulary: A collection of all the unique keywords extracted from the product titles, descriptions, or other relevant fields.
Posting Lists: For each keyword in the vocabulary, there is a corresponding posting list that contains the identifiers of the products that contain that keyword.
When a user performs a search query, the system looks up the keywords in the Inverted Index and retrieves the corresponding posting lists. The posting lists are
then used to fetch the relevant product details from the product database.


Complexity:
Inverted Index:

The Inverted Index algorithm is generally considered efficient for searching based on keywords.
The time complexity of building the index is O(n), where n is the total number of words in all the products. This is a one-time cost incurred during the index construction phase.
The space complexity of the index is O(n), as it stores each unique keyword and its corresponding posting lists.
The time complexity of searching using the index is O(m), where m is the number of products that contain the searched keywords. This is usually much smaller than the total number of products, making the search efficient.
However, the efficiency of the Inverted Index can be further improved by applying techniques such as:
Compression of posting lists to reduce memory usage.
Partitioning of the index to distribute the search load across multiple machines.
Caching of frequently accessed posting lists to reduce disk I/O.
 */
class Product{
    private String title;
    private String description;

    public Product(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}


public class InvertedIndex {
    Map<String, List<Integer>> index;

    public InvertedIndex(){
        index=new HashMap<>();
    }

    public void buildIndex(Map<Integer, Product> products){
        for(Map.Entry<Integer, Product> entry:products.entrySet()){
            int productId=entry.getKey();
            Product product=entry.getValue();
            String title=product.getTitle();
            String description=product.getDescription();

            List<String> keywords = extractKeywords(title, description);

            for(String keyword:keywords){
                index.putIfAbsent(keyword, new ArrayList<>());
                index.get(keyword).add(productId);
            }
        }
    }

    private List<String> extractKeywords(String title, String description) {
        // Perform keyword extraction from title and description
        // This can involve tokenization, lowercasing, removing stopwords, etc.
        // Return a list of extracted keywords
        List<String> keywords = new ArrayList<>();
        // Implement keyword extraction logic here
        return keywords;
    }

    public List<Integer> search(String query){
        String []queryKeywords=query.toLowerCase().split("\\s+");
        Set<Integer>  productIds=new HashSet<>();
        for(String keyword:queryKeywords){
            if(index.containsKey(keyword)) productIds.addAll(index.get(keyword));
        }
        return new ArrayList<>(productIds);
    }
}





























