import java.io.*;
import java.util.*;

class Solution
{
  /**
   * Aussumption: assume the input heading is legal, and there is no illegal input. 
   * For example: 
   * ```
   * H1 some text
   * H5 some text
   * ```
   */
  // record the current index of the input heading list when recursion method is happenning
  //check code in
  private static int index = 0;
    
  public static class Heading {
    protected int weight;
    protected String text;
    
    public Heading(int weight, String text) {
      this.weight = weight;
      this.text = text;
    }
  }
  public static class Node {
    protected Heading heading;
    protected List<Node> children;
    
    public Node(Heading heading) {
      this.heading = heading;
      this.children = new ArrayList<>();
    }
  }
    
  public static void main(String[] args) throws java.lang.Exception
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    List<Heading> headings = new ArrayList<>();
    for (String line = br.readLine(); line != null; line = br.readLine()) {
      headings.add(parse(line));
    }
    Node outline = toOutline(headings);
    String html = toHtml(outline);
    System.out.println(html);
  }
   

  private static Node toOutline(List<Heading> headings) {
    // get the input list headings length
    int listLength = headings.size();
    // create the default heading 0 and add the first input headings 
    // assume the headings length is always bigger that 0, if not then can return null here
    // and so need a node null check in toHtml function
    Node root = new Node(new Heading(0, ""));
    root.children.add(new Node(headings.get(0)));
    index = 1;
    // build the node
    buildNode(root, headings, listLength);
    return root;
  }
    
  /**
   * Build the nested root node object based on the input of the headings using recursion method. 
   * @param root a child node in the final root nodes objects
   * @param headings a list of the input headings
   * @param listLength the length of the input headings list
   */

  private static void buildNode(Node root, List<Heading> headings, int listLength) {
    int rootHeadingWeight = root.heading.weight;
    // if current Heading weight <= child node heading weight then return to previous node
    // else if current Heading weight > child node heading weight + 1 then change the child node to its last child node
    // else if current Heading weight == child node heading weight + 1 then add it to the child node last child node
    while (index < listLength) {
      Heading currentHeading = headings.get(index);
      int currentHeadingWeight = currentHeading.weight;

      if (currentHeadingWeight <= rootHeadingWeight) {
        return;
      } else if (currentHeadingWeight > rootHeadingWeight + 1){
        int lastChildIndex = root.children.size() - 1;
        buildNode(root.children.get(lastChildIndex), headings, listLength);
      } else if (currentHeadingWeight == rootHeadingWeight + 1) {
        Node temp = new Node(currentHeading);
        root.children.add(temp);
        index++;
      }
    }
  }
  
  /** Parses a line of input. 
      This implementation is correct for all predefined test cases. */
  private static Heading parse(String record) {
    String[] parts = record.split(" ", 2);
    int weight = Integer.parseInt(parts[0].substring(1));
    Heading heading = new Heading(weight, parts[1].trim());
    return heading;
  }
  
  /** Converts a node to HTML. 
      This implementation is correct for all predefined test cases. */
  private static String toHtml(Node node) {
    StringBuilder buf = new StringBuilder();
    if (!node.heading.text.isEmpty()) {
      buf.append(node.heading.text);
      buf.append("\n");
    }
    Iterator<Node> iter = node.children.iterator();
    if (iter.hasNext()) {
      buf.append("<ol>");
      
      while (iter.hasNext()) {
        Node child = iter.next();
        buf.append("<li>");
        buf.append(toHtml(child));
        buf.append("</li>");
        if (iter.hasNext()) {
          buf.append("\n");
        }
      }
      buf.append("</ol>");
    }
    return buf.toString();
  }
}
