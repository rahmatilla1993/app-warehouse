package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.*;
import com.example.appwarehouse.repository.ClientRepository;
import com.example.appwarehouse.repository.CurrencyRepository;
import com.example.appwarehouse.repository.OutputRepository;
import com.example.appwarehouse.repository.WareHouseRepository;
import com.example.appwarehouse.transfer.OutputDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Output> getAllOutputs() {
        return outputRepository.findAll();
    }

    public List<Output> getAllOutputsByWarehouseId(Integer warehouse_id) {
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(warehouse_id);
        if (optionalWareHouse.isPresent()) {
            return outputRepository.findAllByWareHouse_Id(warehouse_id);
        }
        return null;
    }

    public List<Output> getAllOutputsByClientId(Integer client_id) {
        Optional<Client> optionalClient = clientRepository.findById(client_id);
        if (optionalClient.isPresent()) {
            return outputRepository.findAllByClient_Id(client_id);
        }
        return null;
    }

    public Result addingOutput(OutputDTO outputDTO, boolean edit) {
        Output output = new Output();
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(outputDTO.getWareHouse_id());
        if (!optionalWareHouse.isPresent()) {
            return new Result("Bunday ombor yo'q", false);
        }
        WareHouse wareHouse = optionalWareHouse.get();
        if (!wareHouse.isActive()) {
            return new Result("Ombor aktiv holatdamas", false);
        }

        Optional<Client> optionalClient = clientRepository.findById(outputDTO.getClient_id());
        if (!optionalClient.isPresent()) {
            return new Result("Client mavjudmas", false);
        }
        Client client = optionalClient.get();
        if (!client.isActive()) {
            return new Result("Client active holatdamas", false);
        }

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDTO.getCurrency_id());
        if (!optionalCurrency.isPresent()) {
            return new Result("Pul birligi mavjudmas", false);
        }
        Currency currency = optionalCurrency.get();
        if (!currency.isActive()) {
            return new Result("Pul birligi aktivmas", false);
        }

        output.setWareHouse(wareHouse);
        output.setClient(client);
        output.setCurrency(currency);
        output.setDate(outputDTO.getDate());
        output.setFactureNumber(outputDTO.getFactureNumber());

        return new Result(true, output);
    }

    public Result addOutput(OutputDTO outputDTO) {

        Result result = addingOutput(outputDTO, false);
        if (result.isSuccess()) {
            Output output = (Output) result.getObject();
            output.setCode(generateOutputCode());
            outputRepository.save(output);
            return new Result("Chiqim saqlandi", true);
        }
        return result;
    }

    public Result deleteOutput(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isPresent()) {
            outputRepository.delete(optionalOutput.get());
            return new Result("Chiqim o'chirildi", true);
        }
        return new Result("Chiqim topilmadi", false);
    }

    public Result editOutput(Integer id, OutputDTO outputDTO) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isPresent()) {
            Result result = addingOutput(outputDTO, true);
            if (result.isSuccess()) {
                Output editOutput = optionalOutput.get();
                Output Output = (Output) result.getObject();
                editOutput.setDate(Output.getDate());
                editOutput.setCurrency(Output.getCurrency());
                editOutput.setFactureNumber(Output.getFactureNumber());
                editOutput.setClient(Output.getClient());
                editOutput.setWareHouse(Output.getWareHouse());
                outputRepository.save(editOutput);
                return new Result("Chiqim o'zgartirildi", true);
            }
            return result;
        }
        return new Result("Chiqim topilmadi", false);
    }

    private String generateOutputCode() {
        List<Output> outputs = getAllOutputs();
        int index = outputs.size();
        if (index == 0) {
            return "1";
        }
        Output output = outputs.get(index - 1);
        index = output.getId() + 1;
        return Integer.toString(index);
    }
}
