package br.com.banco.utils.hatoes;

import br.com.banco.dtos.TransferDTO;
import br.com.banco.dtos.TransferFilterDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.sql.Timestamp;

import static br.com.banco.utils.time.DateUtils.defineTimesStamps;
import static br.com.banco.utils.time.DateUtils.formatTimestampToString;

public class HateoasUtils {

    public static final String BASE_URI = "http://localhost:8080/api/transfer/v1/";

    public static void addHateoasLinks(PagedModel<EntityModel<TransferDTO>> pagedModel, Integer page, TransferFilterDTO filterDTO) {
        addLinkFirstPageHateoas(pagedModel, page,filterDTO);
        addLinkLastPageHateoas(pagedModel, page,filterDTO);
        addLinkPreviewPageHateoas(pagedModel, page,filterDTO);
        addLinkNextPageHateoas(pagedModel, page,filterDTO);
    }

    private static void addLinkFirstPageHateoas(PagedModel<EntityModel<TransferDTO>> pagedModel, Integer page, TransferFilterDTO filterDTO) {
        String baseUri = BASE_URI + filterDTO.getIdAccount();

        StringBuilder uriBuilder = new StringBuilder(baseUri);

        Timestamp[] timestamps = defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate());
        String startDate = formatTimestampToString(timestamps[0]);
        String endDate = formatTimestampToString(timestamps[1]);

        if(page > 0) {
            if (filterDTO.getTransactionOperatorName() != null) {
                uriBuilder.append("?page=0")
                        .append("&startDate=").append(startDate)
                        .append("&endDate=").append(endDate)
                        .append("&transactionOperatorName=")
                        .append(filterDTO.getTransactionOperatorName());
            } else {
                uriBuilder.append("?page=0")
                        .append("&startDate=").append(startDate)
                        .append("&endDate=").append(endDate);
            }
            pagedModel.add(
                    Link.of(uriBuilder.toString()).withRel("first")
            );
        }
    }

    private static void addLinkLastPageHateoas(PagedModel<EntityModel<TransferDTO>> pagedModel, Integer page, TransferFilterDTO filterDTO) {
        String baseUri = BASE_URI + filterDTO.getIdAccount();

        StringBuilder uriBuilder = new StringBuilder(baseUri);

        Timestamp[] timestamps = defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate());
        String startDate = formatTimestampToString(timestamps[0]);
        String endDate = formatTimestampToString(timestamps[1]);

        int lastPage = (int) (pagedModel.getMetadata().getTotalPages() - 1);
        if(page < lastPage) {
            if (filterDTO.getTransactionOperatorName() != null) {
                uriBuilder.append("?page=")
                        .append(lastPage)
                        .append("&startDate=")
                        .append(startDate)
                        .append("&endDate=")
                        .append(endDate).append("&transactionOperatorName=")
                        .append(filterDTO.getTransactionOperatorName());
            } else {
                uriBuilder.append("?page=")
                        .append(lastPage)
                        .append("&startDate=")
                        .append(startDate)
                        .append("&endDate=")
                        .append(endDate);
            }
            pagedModel.add(
                    Link.of(uriBuilder.toString()).withRel("last")
            );
        }
    }
    private static void addLinkPreviewPageHateoas(PagedModel<EntityModel<TransferDTO>> pagedModel, Integer page, TransferFilterDTO filterDTO) {
        String baseUri = BASE_URI + filterDTO.getIdAccount();

        StringBuilder uriBuilder = new StringBuilder(baseUri);

        Timestamp[] timestamps = defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate());
        String startDate = formatTimestampToString(timestamps[0]);
        String endDate = formatTimestampToString(timestamps[1]);

        if (page > 0) {
            if (filterDTO.getTransactionOperatorName() != null) {
                uriBuilder.append("?page=")
                        .append(page - 1)
                        .append("&startDate=")
                        .append(startDate)
                        .append("&endDate=")
                        .append(endDate).append("&transactionOperatorName=")
                        .append(filterDTO.getTransactionOperatorName());
            } else {
                uriBuilder.append("?page=")
                        .append(page - 1)
                        .append("&startDate=")
                        .append(startDate)
                        .append("&endDate=")
                        .append(endDate);
            }
            pagedModel.add(
                    Link.of(uriBuilder.toString()).withRel("preview")
            );
        }
    }

    private static void addLinkNextPageHateoas(PagedModel<EntityModel<TransferDTO>> pagedModel, Integer page, TransferFilterDTO filterDTO) {
        String baseUri = BASE_URI + filterDTO.getIdAccount();

        StringBuilder uriBuilder = new StringBuilder(baseUri);

        Timestamp[] timestamps = defineTimesStamps(filterDTO.getStartDate(), filterDTO.getEndDate());
        String startDate = formatTimestampToString(timestamps[0]);
        String endDate = formatTimestampToString(timestamps[1]);

        int lastPage = (int) (pagedModel.getMetadata().getTotalPages() - 1);
        if (page < lastPage) {
            if (filterDTO.getTransactionOperatorName() != null) {
                uriBuilder.append("?page=")
                        .append(page + 1)
                        .append("&startDate=")
                        .append(startDate)
                        .append("&endDate=")
                        .append(endDate).append("&transactionOperatorName=")
                        .append(filterDTO.getTransactionOperatorName());
            } else {
                uriBuilder.append("?page=")
                        .append(page + 1)
                        .append("&startDate=")
                        .append(startDate)
                        .append("&endDate=")
                        .append(endDate);
            }
            pagedModel.add(
                    Link.of(uriBuilder.toString()).withRel("next")
            );
        }
    }
}
