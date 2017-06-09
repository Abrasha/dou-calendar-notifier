package com.github.abrasha.dounotifier.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Andrii Abramov on 6/8/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    String title;
    String content;
    String whenAndWhere;
    Set<String> tags;

}
