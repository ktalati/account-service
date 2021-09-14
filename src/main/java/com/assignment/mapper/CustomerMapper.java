package com.assignment.mapper;

import com.assignment.entity.Customer;
import com.assignment.model.CustomerResponse;
import com.assignment.model.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "currency", constant = "Euro")
    @Mapping(target = "accountId", source = "customer.account.id")
    @Mapping(target = "firstName", source = "customer.firstName")
    @Mapping(target = "surname", source = "customer.lastName")
    @Mapping(target = "middleName", source = "customer.middleName")
    @Mapping(target = "balance", source = "transactionResponse.balance")
    @Mapping(target = "accountResponse", source = "transactionResponse.transactions")
    CustomerResponse map(Customer customer, TransactionResponse transactionResponse);

}
