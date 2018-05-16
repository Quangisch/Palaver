package de.web.ngthi.palaver.model;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User implements Comparable<User>{

    private String username;

    public boolean isLocalUser() {
        return false;
    }

    public boolean equals(Object o) {
        if(o == null || !(o instanceof User))
            return false;
        return username.equals(((User) o).username);
    }

    public String toString() {
        return username;
    }

    @Override
    public int compareTo(@NonNull User o) {
        return username.compareTo(o.username);
    }
}
