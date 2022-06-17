package mwhelan;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * PROBLEM:
 * The pager gets the following data as input:
 * - total number of items
 * - number of items per page
 * - offset e.g. number of items flipped through
 * The pager should answer the following questions:
 * - how many pages are there?
 * - should prev/next link be visible?
 * - which links to print?
 * - which page is current?
 *
 * @author Mike Lowe
 */
public final class Pager {

  private int numberOfItems;
  private int itemsPerPage;
  private int offset;
  private int numberOfPages;
  private int currentPage;
  private boolean prevVisible;
  private boolean nextVisible;
  private final Set<Integer> linksToPrint = new LinkedHashSet<>();

  public void update(final int numberOfItems, final int itemsPerPage, final int offset) {
    setNumberOfItems(numberOfItems);
    setItemsPerPage(itemsPerPage);
    setOffset(offset);
    updateNumberOfPages();
    updateCurrentPage();
    updatePrevVisible();
    updateNextVisible();
    updateLinksToPrint();
  }

  private void setNumberOfItems(final int numberOfItems) {
    this.numberOfItems = numberOfItems;
    if (this.numberOfItems < 0) {
      throw new IllegalArgumentException("number of items cannot be less than 0");
    }
  }

  public int getNumberOfItems() {
    return numberOfItems;
  }

  private void setItemsPerPage(final int itemsPerPage) {
    this.itemsPerPage = itemsPerPage;
    if (this.itemsPerPage < 1) {
      throw new IllegalArgumentException("items per page cannot be less than 1");
    }
  }

  public int getItemsPerPage() {
    return itemsPerPage;
  }

  private void setOffset(final int offset) {
    this.offset = offset;
    if (this.offset < 0) {
      throw new IllegalArgumentException("offset cannot be less than 0");
    } else if (this.offset > this.numberOfItems) {
      throw new IllegalArgumentException("offset cannot be greater than number of items");
    }
  }

  public int getOffset() {
    return offset;
  }

  private void updateNumberOfPages() {
    if (numberOfItems == 0) {
      numberOfPages = 1;
    } else {
      numberOfPages = numberOfItems / itemsPerPage;
      if (numberOfItems % itemsPerPage != 0) {
        numberOfPages++;
      }
    }
  }

  public int getNumberOfPages() {
    return numberOfPages;
  }

  private void updateCurrentPage() {
    if (offset == 0) {
      currentPage = 1;
    } else {
      currentPage = offset / itemsPerPage;
      if (offset % itemsPerPage != 0) {
        currentPage++;
      }
    }
  }

  public int getCurrentPage() {
    return currentPage;
  }

  private void updatePrevVisible() {
    prevVisible = currentPage > 1;
  }

  public boolean isPrevVisible() {
    return prevVisible;
  }

  private void updateNextVisible() {
    nextVisible = currentPage < numberOfPages;
  }

  public boolean isNextVisible() {
    return nextVisible;
  }

  private void updateLinksToPrint() {
    linksToPrint.clear();
    if (isPrevVisible()) {
      linksToPrint.add(currentPage - 1);
    }
    linksToPrint.add(currentPage);
    if (isNextVisible()) {
      linksToPrint.add(currentPage + 1);
    }
  }

  public Set<Integer> getLinksToPrint() {
    return new LinkedHashSet<>(linksToPrint);
  }

}
