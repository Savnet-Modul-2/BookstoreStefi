package com.example.BookStoreSpring.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "librarian")
@Table(name = "librarians", schema = "public")
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Boolean verifiedAccount = false;

    @Column
    private String verificationCode;

    @Column
    private LocalDateTime verificationCodeGenerationTime;

    @Column
    private Boolean loggedIn = false;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "LIBRARY_ID", referencedColumnName = "ID")
    private Library library;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getVerifiedAccount() {
        return verifiedAccount;
    }

    public void setVerifiedAccount(Boolean verifiedAccount) {
        this.verifiedAccount = verifiedAccount;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getVerificationCodeGenerationTime() {
        return verificationCodeGenerationTime;
    }

    public void setVerificationCodeGenerationTime(LocalDateTime verificationCodeGenerationTime) {
        this.verificationCodeGenerationTime = verificationCodeGenerationTime;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
