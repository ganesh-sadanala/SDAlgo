package src.SearchAlgo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/*
Faceted Search is an algorithm that allows users to filter and refine search results based on multiple dimensions or attributes (facets) of the products.
It provides a flexible and interactive way for users to narrow down their search results by selecting specific values for each facet.

Here's how the Faceted Search algorithm works:

Data Preparation:
The product data is organized and structured with relevant attributes or facets that can be used for filtering. Common facets include brand, category, price range, color, size, etc.
Each product is associated with its corresponding facet values.
Facet Selection:
The user interface presents a set of facets or filtering options to the user.
Each facet represents a specific attribute or dimension of the products.
Users can select one or more values for each facet to define their filtering criteria.
Filtering:
When the user selects values for one or more facets, the Faceted Search algorithm applies the corresponding filters to the product dataset.
The algorithm iterates over each product and checks if it matches the selected facet values.
Products that satisfy all the selected facet values are included in the filtered result set.
Result Display:
The filtered products are presented to the user as the search results.
The search results are dynamically updated whenever the user modifies the facet selections.
The user can further refine the search results by selecting additional facet values or deselecting previously selected values.
Facet Aggregation (optional):
In addition to filtering the products, the Faceted Search algorithm can also calculate and display the count or distribution of products across different facet values.
This allows users to see the number of products available for each facet value and helps them make informed decisions while refining their search.
Pagination and Sorting (optional):
If the search results are large, pagination can be implemented to display a subset of products at a time.
Sorting options can be provided to allow users to sort the search results based on relevant criteria such as price, popularity, or relevance.


Complexity:

The efficiency of the Faceted Search algorithm depends on the number of facets, the number of products, and the filtering criteria.
In the implementation provided above, the time complexity of searching is O(n * f), where n is the number of products and f is the number of facets.
This is because the algorithm iterates over each product and applies the filtering criteria for each selected facet.
The space complexity is O(n), as it stores the product dataset.
To improve the efficiency of Faceted Search, the following optimizations can be considered:
Indexing: Building indexes or data structures that allow for quick filtering based on facet values. This can reduce the need to iterate over all products for each facet.
Caching: Caching the filtered results for commonly used facet combinations to avoid redundant computations.
Lazy loading: Loading only a subset of products initially and fetching more products as the user scrolls or navigates through the results.
Pagination: Implementing pagination to limit the number of products processed and displayed at a time.
Parallel processing: Distributing the filtering and aggregation tasks across multiple threads or machines to handle large datasets.
*/
class Product {
    private String title;
    private String description;
    private String brand;
    private String category;
    private double price;

    public Product(String title, String description, String brand, String category, double price) {
        this.title = title;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

public class FacetedSearch {
    private List<Product> products;

    public FacetedSearch(List<Product> products) {
        this.products = products;
    }

    List<Product> search(Map<String, List<String>> filters){
        List<Product> filteredProducts=new ArrayList<>(products);

        for(Map.Entry<String, List<String>> entry: filters.entrySet()){
            String facet= entry.getKey();
            List<String> selectedValues=entry.getValue();

            filteredProducts=filterByFacet(filteredProducts, facet, selectedValues);
        }

        return filteredProducts;
    }

    List<Product> filterByFacet(List<Product> products, String facet, List<String> selectedValues){
        List<Product> filteredProducts=new ArrayList<>();
        for (Product product : products) {
            switch (facet) {
                case "brand":
                    if (selectedValues.contains(product.getBrand())) {
                        filteredProducts.add(product);
                    }
                    break;
                case "category":
                    if (selectedValues.contains(product.getCategory())) {
                        filteredProducts.add(product);
                    }
                    break;
                case "price":
                    for (String value : selectedValues) {
                        String[] range = value.split("-");
                        double minPrice = Double.parseDouble(range[0]);
                        double maxPrice = Double.parseDouble(range[1]);
                        if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                            filteredProducts.add(product);
                            break;
                        }
                    }
                    break;
                // Add more cases for other facets as needed
            }
        }

        return filteredProducts;
    }
}
















