/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.springlets.data.domain;

import org.springframework.util.Assert;

/**
 * Defines a global text search. This Value Object is used to define
 * global data searches in an entity or group of entities to validate
 * whether any or some of the data of the entity meet the search.
 *
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
public final class GlobalSearch {

  private final String searchText;
  private final boolean regexp;

  /**
   * Create a new instance of global text search.
   *
   * @param text the String text to search the data
   * @param regexp indicates if the text provided is a regular expression,
   *        or just a text to find
   */
  public GlobalSearch(String text, boolean regexp) {
    Assert.hasText(text, "The global search text must be non empty");
    this.searchText = text;
    this.regexp = regexp;
  }

  /**
   * Create a new search instance of global text by values that contains
   * the provided text.
   *
   * @param text the String text to find
   */
  public GlobalSearch(String text) {
    this(text, false);
  }

  /**
   * Returns the text to find or the regular expression to use in search.
   *
   * @return the search text
   */
  public String getText() {
    return searchText;
  }

  /**
   * Indicates if the search text is a regular expression or just text to
   * search.
   *
   * @return true if the search text is a regular expression. false if not.
   */
  public boolean isRegexp() {
    return regexp;
  }

  /**
   * Indicates if some text matches with the search criteria.
   *
   * @param text the String text to search
   * @return if the provided text matches with search
   */
  public boolean matches(String text) {
    if (text == null || text.length() == 0) {
      return false;
    }

    if (isRegexp()) {
      return text.matches(searchText);
    } else {
      return text.contains(searchText);
    }
  }
}