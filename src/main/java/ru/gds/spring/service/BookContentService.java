package ru.gds.spring.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.gds.spring.interfaces.ContentService;
import ru.gds.spring.mongo.domain.BookContent;
import ru.gds.spring.mongo.dto.BookContentDto;
import ru.gds.spring.mongo.dto.PageDto;
import ru.gds.spring.mongo.repository.BookContentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookContentService implements ContentService {

    private static final Logger logger = Logger.getLogger(BookContentService.class);

    private final BookContentRepository bookContentRepository;

    public BookContentService(BookContentRepository bookContentRepository) {
        this.bookContentRepository = bookContentRepository;
    }

    public BookContentDto getPagesForBook(String order) {
        return new BookContentDto(getPages(order));
    }

    public List<BookContentDto> getPagesForBooks(List<String> orders) {
        List<BookContentDto> result = new ArrayList<>();
        for (String order : orders) {
            result.add(new BookContentDto(getPages(order)));

        }
        return result;
    }

    public List<PageDto> getPages(String bookId) {
        System.out.println("getPages for bookId " + bookId);
        List<PageDto> pageDtoList = new ArrayList<>();

        try {
            String[] sentences = bookContentRepository.findById(bookId)
                    .map(BookContent::getText)
                    .map(t -> t.split("[.!?]\\s*")).orElse(null);

            if (sentences == null)
                return pageDtoList;

            int countSentences = 7;
            int index = 0;
            int pageNumber = 0;

            StringBuilder page = new StringBuilder();

            for (int i = 0; i < sentences.length; i++) {

                if (index == countSentences || i == sentences.length - 1) {
                    PageDto pageDto = new PageDto();
                    pageDto.setText(page.toString());
                    pageDto.setPage(++pageNumber);
                    pageDtoList.add(pageDto);

                    page = new StringBuilder();
                    index = 0;
                }
                page.append(sentences[i]).append(". ");
                index += 1;
            }
        } catch (Exception e) {
            logger.error("getPages error: " + Arrays.asList(e.getStackTrace()));
        }

        System.out.println("getPages for bookId" + bookId + " done");
        return pageDtoList;
    }
}
