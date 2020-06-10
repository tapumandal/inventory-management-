package com.tapumandal.ims.service.implementation;

import com.tapumandal.ims.entity.Measurement;
import com.tapumandal.ims.repository.MeasurementRepository;
import com.tapumandal.ims.repository.ProductRepository;
import com.tapumandal.ims.service.MeasurementService;
import com.tapumandal.ims.service.ProductService;
import com.tapumandal.ims.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    private Measurement measurement;

    public MeasurementServiceImpl(){}

    public MeasurementServiceImpl(Measurement measurement){
        this.measurement = measurement;
    }

    @Override
    public Measurement create(Measurement meas) {
        Optional<Measurement> measurement;

        try{
            measurement = Optional.ofNullable(measurementRepository.create(meas));
        }catch (Exception e){
            return null;
        }

        if(measurement.isPresent()){
            return measurement.get();
        }else{
            return null;
        }
    }

    @Override
    public Measurement update(Measurement meas) {

        Optional<Measurement> measurement;
        try{
            measurement = Optional.ofNullable(measurementRepository.update(meas));
        }catch (Exception e){
            return null;
        }

        if(measurement.isPresent()){
            return measurement.get();
        }else{
            return null;
        }

    }

    @Override
    public List<Measurement> getAll(Pageable pageable) {
        Optional<List<Measurement>> products = Optional.ofNullable(measurementRepository.getAll(pageable));

        if(products.isPresent()){
            return products.get();
        }else{
            return null;
        }
    }

    @Override
    public Measurement getById(int id) {

        Optional<Measurement> measurement = Optional.ofNullable(measurementRepository.getById(id));

        if(measurement.isPresent()){
            return measurement.get();
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            return measurementRepository.delete(id);
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public Measurement getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<Measurement> getAllByValue(String kye, String value) {
        return null;
    }

    @Override
    public boolean isActive(int id) {
        return measurement.isActive();
    }

    @Override
    public boolean isDeleted(int id) {
        return measurement.isDeleted();
    }

    @Override
    public MyPagenation getPageable(Pageable pageable) {
        return measurementRepository.getPageable(pageable);
    }


}