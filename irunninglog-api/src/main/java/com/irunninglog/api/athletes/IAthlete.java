package com.irunninglog.api.athletes;

public interface IAthlete {

    long getId();

    IAthlete setId(long id);

    String getEmail();

    IAthlete setEmail(String email);

    String getFirstname();

    IAthlete setFirstname(String firstname);

    String getLastname();

    IAthlete setLastname(String lastname);

    String getAvatar();

    IAthlete setAvatar(String avatar);

}
