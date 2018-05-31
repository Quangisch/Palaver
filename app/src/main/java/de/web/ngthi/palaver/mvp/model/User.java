package de.web.ngthi.palaver.mvp.model;

import android.support.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User implements Comparable<User>{

    private String username;

    public boolean isLocalUser() {
        return false;
    }

    public int hashCode() {
        if(username != null)
            return username.hashCode();
        return super.hashCode();
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
        return username.compareToIgnoreCase(o.username);
    }
}
