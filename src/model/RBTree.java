package model;

import java.util.ArrayList;

import static model.Constants.BLACK;
import static model.Constants.RED;

public class RBTree<T extends Comparable<T>> extends BST<T> {
  @Override
  public boolean insert(T element) {
    if (super.insert(element)) {
      ensureRBTree(element);
      return true;
    }
    return false;
  }

  private void ensureRBTree(T e) {
    ArrayList<Node<T>> path = path(e);
    int i = path.size() - 1;
    Node<T> u = (path.get(i));
    Node<T> v = (u == root) ? null : (path.get(i - 1));
    u.color = RED;
    if (u == root)
      u.color = BLACK;
    else if (v != null & v.color == RED)
      fixDoubleRed(u, v, path, i);
  }

  private void fixDoubleRed(Node<T> u, Node<T> v, ArrayList<Node<T>> path, int i) {
    Node<T> w = (path.get(i - 2));
    Node<T> parentOfw = (w == root) ? null : path.get(i - 3);

    Node<T> x = (w.left == v) ? (w.right) : (w.left);

    if (x == null || x.color == BLACK) {
      if (w.left == v && v.left == u) {
        restructureRecolor(u, v, w, w, parentOfw);

        w.left = v.right; // v.right is y3 in Figure 48.6
        v.right = w;
      } else if (w.left == v && v.right == u) {
        restructureRecolor(v, u, w, w, parentOfw);
        v.right = u.left;
        w.left = u.right;
        u.left = v;
        u.right = w;
      } else if (w.right == v && v.right == u) {
        restructureRecolor(w, v, u, w, parentOfw);
        w.right = v.left;
        v.left = w;
      } else {
        restructureRecolor(w, u, v, w, parentOfw);
        w.right = u.left;
        v.left = u.right;
        u.left = w;
        u.right = v;
      }
    } else {
      w.color = RED;
      u.color = RED;
      ((w.left)).color = BLACK;
      ((w.right)).color = BLACK;

      if (w == root) {
        w.color = BLACK;
      } else if (parentOfw != null && (parentOfw).color == RED) {
        u = w;
        v = parentOfw;
        fixDoubleRed(u, v, path, i - 2);
      }
    }
  }

  private void restructureRecolor(Node<T> a, Node<T> b, Node<T> c, Node<T> w, Node<T> parentOfw) {
    if (parentOfw == null)
      root = b;
    else if (parentOfw.left == w)
      parentOfw.left = b;
    else
      parentOfw.right = b;

    b.color = BLACK;
    a.color = RED;
    c.color = RED;
  }

  @Override
  public boolean delete(T e) {
    Node<T> current = root;
    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        current = current.left;
      } else if (e.compareTo(current.element) > 0) {
        current = current.right;
      } else
        break;
    }

    if (current == null)
      return false;

    java.util.ArrayList<Node<T>> path;
    if (current.left != null && current.right != null) {
      Node<T> rightMost = current.left;
      while (rightMost.right != null) {
        rightMost = rightMost.right;
      }
      path = path(rightMost.element);
      current.element = rightMost.element;
    } else
      path = path(e);
    deleteLastNodeInPath(path);
    return true;
  }

  public void deleteLastNodeInPath(ArrayList<Node<T>> path) {
    int i = path.size() - 1;
    Node<T> u = (path.get(i));
    Node<T> parentOfu = (u == root) ? null : (path.get(i - 1));
    Node<T> grandparentOfu = (parentOfu == null ||
            parentOfu == root) ? null : (path.get(i - 2));
    Node<T> childOfu = (u.left == null) ? (u.right) : (u.left);

    // Delete node u. Connect childOfu with parentOfu
    connectNewParent(parentOfu, u, childOfu);

    // Recolor the nodes and fix double black if needed
    if (childOfu == root || u.color == RED)
      return; // Done if childOfu is root or if u is red 
    else if (childOfu != null && childOfu.color == RED)
      childOfu.color = BLACK; // Set it black, done
    else // u is black, childOfu is null or black
      // Fix double black on parentOfu
      fixDoubleBlack(grandparentOfu, parentOfu, childOfu, path, i);
  }

  private void fixDoubleBlack(
          Node<T> grandparent, Node<T> parent,
          Node<T> db, ArrayList<Node<T>> path, int i) {
    // Obtain y, y1, and y2
    Node<T> y = (parent.right == db) ? (parent.left) : (parent.right);
    Node<T> y1 = (y.left);
    Node<T> y2 = (y.right);

    if (y.color == BLACK && y1 != null && y1.color == RED) {
      if (parent.right == db) {
        // Case 1.1: y is a left black sibling and y1 is red
        connectNewParent(grandparent, parent, y);
        recolor(parent, y, y1); // Adjust colors

        // Adjust child links
        parent.left = y.right;
        y.right = parent;
      } else {
        // Case 1.3: y is a right black sibling and y1 is red        
        connectNewParent(grandparent, parent, y1);
        recolor(parent, y1, y); // Adjust colors

        // Adjust child links
        parent.right = y1.left;
        y.left = y1.right;
        y1.left = parent;
        y1.right = y;
      }
    } else if (y.color == BLACK && y2 != null && y2.color == RED) {
      if (parent.right == db) {
        // Case 1.2: y is a left black sibling and y2 is red
        connectNewParent(grandparent, parent, y2);
        recolor(parent, y2, y); // Adjust colors

        // Adjust child links
        y.right = y2.left;
        parent.left = y2.right;
        y2.left = y;
        y2.right = parent;
      } else {
        // Case 1.4: y is a right black sibling and y2 is red        
        connectNewParent(grandparent, parent, y);
        recolor(parent, y, y2); // Adjust colors

        // Adjust child links
        y.left = parent;
        parent.right = y1;
      }
    } else if (y.color == BLACK) {
      // Case 2: y is black and y's children are black or null
      y.color = RED; // Change y to red
      if (parent.color == RED)
        parent.color = BLACK; // Done
      else if (parent != root) {
        // Propagate double black to the parent node
        // Fix new appearance of double black recursively
        db = parent;
        parent = grandparent;
        grandparent =
                (i >= 3) ? (Node<T>) (path.get(i - 3)) : null;
        fixDoubleBlack(grandparent, parent, db, path, i - 1);
      }
    } else { // y.color == RED
      if (parent.right == db) {
        parent.left = y2;
        y.right = parent;
      } else {
        parent.right = y.left;
        y.left = parent;
      }

      parent.color = RED; // Color parent red
      y.color = BLACK; // Color y black
      connectNewParent(grandparent, parent, y); // y is new parent
      fixDoubleBlack(y, parent, db, path, i - 1);
    }
  }

  public boolean getRed(Node<T> e) {
    Node<T> aNode = e;
    boolean b = true;
    if (aNode.color == BLACK) {
      b = false;
    }
    return b;
  }

  private void recolor(Node<T> parent, Node<T> newParent, Node<T> c) {
    if (parent.color == RED)
      newParent.color = RED;
    else
      newParent.color = BLACK;

    parent.color = BLACK;
    c.color = BLACK;
  }

  private void connectNewParent(Node<T> grandparent, Node<T> parent, Node<T> newParent) {
    if (parent == root) {
      root = newParent;
      if (root != null)
        newParent.color = BLACK;
    } else if (grandparent.left == parent)
      grandparent.left = newParent;
    else
      grandparent.right = newParent;
  }
}
