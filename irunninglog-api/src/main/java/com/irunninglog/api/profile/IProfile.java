package com.irunninglog.api.profile;

public interface IProfile {

    IProfile setId(long id);

    IProfile setUsername(String email);

    IProfile setFirstName(String firstName);

    IProfile setLastName(String lastName);

    IProfile setAvatar(String avatar);

    long getId();

    String getUsername();

    String getFirstName();

    String getLastName();

    String getAvatar();

}
