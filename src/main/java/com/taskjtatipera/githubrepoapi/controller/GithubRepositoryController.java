package com.taskjtatipera.githubrepoapi.controller;

import com.taskjtatipera.githubrepoapi.service.GithubRepositoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GithubRepositoryController {

    private final GithubRepositoryService githubRepoService;

    public GithubRepositoryController(GithubRepositoryService githubRepoService) {
        this.githubRepoService = githubRepoService;
    }

    @GetMapping(path="/repositories/{username}", produces="application/json")
    public ResponseEntity<?> getRepositories(@PathVariable String username) {
        return githubRepoService.getRepositories(username);
    }
}
