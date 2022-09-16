package com.xpand.challenge.controller;

import com.xpand.challenge.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MovieControllerTest {

    @InjectMocks
    private MovieController controller;

    @Mock
    private MovieServiceImpl service;



}