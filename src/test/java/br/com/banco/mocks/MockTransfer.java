package br.com.banco.mocks;

import br.com.banco.dtos.TransferDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MockTransfer {

   public TransferDTO createTransferDTO(BigDecimal amount) {
      TransferDTO transferDTO = new TransferDTO();
      transferDTO.setId(1L);
      transferDTO.setTransferDate(new Timestamp(System.currentTimeMillis()));
      transferDTO.setAmount(amount);
      transferDTO.setType("Type");
      transferDTO.setTransactionOperatorName("Operator");
      return transferDTO;
   }

   public List<TransferDTO> createMockTransfers() {
      List<TransferDTO> transfers = new ArrayList<>();

      TransferDTO transfer1 = new TransferDTO();
      transfer1.setId(1L);
      transfer1.setTransferDate(new Timestamp(System.currentTimeMillis()));
      transfer1.setAmount(new BigDecimal("100.00"));
      transfer1.setType("Credit");
      transfer1.setTransactionOperatorName("John Doe");
      transfers.add(transfer1);

      TransferDTO transfer2 = new TransferDTO();
      transfer2.setId(2L);
      transfer2.setTransferDate(new Timestamp(System.currentTimeMillis()));
      transfer2.setAmount(new BigDecimal("50.00"));
      transfer2.setType("Debit");
      transfer2.setTransactionOperatorName("Jane Smith");
      transfers.add(transfer2);

      TransferDTO transfer3 = new TransferDTO();
      transfer3.setId(3L);
      transfer3.setTransferDate(new Timestamp(System.currentTimeMillis()));
      transfer3.setAmount(new BigDecimal("75.00"));
      transfer3.setType("Credit");
      transfer3.setTransactionOperatorName("John Doe");
      transfers.add(transfer3);

      TransferDTO transfer4 = new TransferDTO();
      transfer4.setId(4L);
      transfer4.setTransferDate(new Timestamp(System.currentTimeMillis()));
      transfer4.setAmount(new BigDecimal("60.00"));
      transfer4.setType("Debit");
      transfer4.setTransactionOperatorName("Jane Smith");
      transfers.add(transfer4);

      return transfers;
   }
}
