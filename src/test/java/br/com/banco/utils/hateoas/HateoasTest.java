package br.com.banco.utils.hateoas;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.dtos.TransferFilterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static br.com.banco.utils.hatoes.HateoasUtils.*;
import static br.com.banco.utils.time.DateUtils.defineTimesStamps;
import static br.com.banco.utils.time.DateUtils.formatTimestampToString;
import static org.junit.jupiter.api.Assertions.*;

class HateoasTest {
    
    public static final String BASE_URL = "http://localhost:8080/api/transfer/v1/12345";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLinkFirstPageHateoas() {
        PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(new ArrayList<>(), new PagedModel.PageMetadata(10, 5, 100));

        Integer page = 1;
        TransferFilterDTO filterDTO = new TransferFilterDTO();
        filterDTO.setIdAccount(12345L);
        filterDTO.setStartDate(new Timestamp(1626211200000L));
        filterDTO.setEndDate(new Timestamp(1626297600000L));

        List<Link> links = pagedModel.getLinks().toList();
        assertTrue(links.isEmpty());

        addLinkFirstPageHateoas(pagedModel, page, filterDTO);

        links = pagedModel.getLinks().toList();
        assertFalse(links.isEmpty());
        assertEquals(1, links.size());

        Link link = links.get(0);

        assertEquals("first", String.valueOf(link.getRel()));
        assertEquals(BASE_URL + "?page=0&startDate=2021-07-13%2018:20:00&endDate=2021-07-14%2018:20:00", link.getHref());
    }

    @Test
    void testAddLinkLastPageHateoas() {
        PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(new ArrayList<>(), new PagedModel.PageMetadata(10, 5, 100));

        Integer page = 1;
        TransferFilterDTO filterDTO = new TransferFilterDTO();
        filterDTO.setIdAccount(12345L);
        filterDTO.setStartDate(new Timestamp(1626211200000L));
        filterDTO.setEndDate(new Timestamp(1626297600000L));

        addLinkLastPageHateoas(pagedModel, page, filterDTO);

        List<Link> links = pagedModel.getLinks().toList();
        assertFalse(links.isEmpty());
        assertEquals(1, links.size());

        Link link = links.get(0);

        int lastPage = (int) (pagedModel.getMetadata().getTotalPages());
        String expectedUrl = BASE_URI + filterDTO.getIdAccount()
                + "?page=" + lastPage
                + "&startDate=" + formatTimestampToString(defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate())[0])
                + "&endDate=" + formatTimestampToString(defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate())[1]);

        assertEquals("last", link.getRel().value());
        assertEquals(expectedUrl, link.getHref());
    }

    @Test
    void testAddLinkPreviewPageHateoas() {
        PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(new ArrayList<>(), new PagedModel.PageMetadata(10, 5, 100));

        Integer page = 1;
        TransferFilterDTO filterDTO = new TransferFilterDTO();
        filterDTO.setIdAccount(12345L);
        filterDTO.setStartDate(new Timestamp(1626211200000L));
        filterDTO.setEndDate(new Timestamp(1626297600000L));

        addLinkPreviewPageHateoas(pagedModel, page, filterDTO);

        List<Link> links = pagedModel.getLinks().toList();
        assertFalse(links.isEmpty());
        assertEquals(1, links.size());

        Link link = links.get(0);

        int previewPage = page - 1;
        String expectedUrl = BASE_URI + filterDTO.getIdAccount()
                + "?page=" + previewPage
                + "&startDate=" + formatTimestampToString(defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate())[0])
                + "&endDate=" + formatTimestampToString(defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate())[1]);

        assertEquals("preview", link.getRel().value());
        assertEquals(expectedUrl, link.getHref());
    }

    @Test
    void testAddLinkNextPageHateoas() {
        PagedModel<EntityModel<TransferDTO>> pagedModel = PagedModel.of(new ArrayList<>(), new PagedModel.PageMetadata(10, 5, 100));

        Integer page = 1;
        TransferFilterDTO filterDTO = new TransferFilterDTO();
        filterDTO.setIdAccount(12345L);
        filterDTO.setStartDate(new Timestamp(1626211200000L));
        filterDTO.setEndDate(new Timestamp(1626297600000L));

        addLinkNextPageHateoas(pagedModel, page, filterDTO);

        List<Link> links = pagedModel.getLinks().toList();
        assertFalse(links.isEmpty());
        assertEquals(1, links.size());

        Link link = links.get(0);

        int nextPage = page + 1;
        String expectedUrl = BASE_URI + filterDTO.getIdAccount()
                + "?page=" + nextPage
                + "&startDate=" + formatTimestampToString(defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate())[0])
                + "&endDate=" + formatTimestampToString(defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate())[1]);

        assertEquals("next", link.getRel().value());
        assertEquals(expectedUrl, link.getHref());
    }
}
