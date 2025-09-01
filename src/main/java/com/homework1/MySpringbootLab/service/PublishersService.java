package com.homework1.MySpringbootLab.service;

import com.homework1.MySpringbootLab.controller.dto.PublisherDTO;
import com.homework1.MySpringbootLab.entity.Publisher;
import com.homework1.MySpringbootLab.repository.PublisherRepository;
import com.homework1.MySpringbootLab.support.BusinessException;
import com.homework1.MySpringbootLab.support.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class PublishersService {
    private final PublisherRepository publisherRepository;

    // 모든 출판사를 조회하며, 각 출판사의 도서 수를 포함합니다.
    public List<PublisherDTO.SimpleResponse> getAllPublishers(){
        return this.publisherRepository.findAll().stream().map(PublisherDTO.SimpleResponse::fromEntity).toList();
    }

    // ID로 특정 출판사를 조회하며, 해당 출판사의 모든 도서 정보를 포함합니다.
    public PublisherDTO.Response getPublisherById(Long id){
        return this.publisherRepository.findByIdWithBooks(id).map(PublisherDTO.Response::fromEntity).orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    // 이름으로 특정 출판사를 조회합니다.
    public PublisherDTO.Response getPublisherByName(String name){
        return this.publisherRepository.findByName(name).map(PublisherDTO.Response::fromEntity).orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    // 새로운 출판사를 생성합니다. 이름 중복을 검증합니다.
    @Transactional
    public PublisherDTO.Response createPublisher(PublisherDTO.Request request){
        Publisher _pub = Publisher.builder()
                .name(request.getName())
                .establishedDate(request.getEstablishedDate())
                .address(request.getAddress())
                .build();

        Publisher savedPub = this.publisherRepository.save(_pub);
        return PublisherDTO.Response.fromEntity(savedPub);
    }

    // 기존 출판사 정보를 수정합니다. 이름 중복(자신 제외)을 검증합니다.
    @Transactional
    public PublisherDTO.Response updatePublisher(Long id, PublisherDTO.Request request){
        return publisherRepository.findById(id).map(existing -> {
            if(request.getName() != null){
                existing.setName(request.getName());
            }

            if(request.getEstablishedDate() != null){
                existing.setEstablishedDate(request.getEstablishedDate());
            }

            if(request.getAddress() != null){
                existing.setAddress(request.getAddress());
            }

            Publisher savedPub = this.publisherRepository.save(existing);
            return PublisherDTO.Response.fromEntity(savedPub);
        }).orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    // 출판사를 삭제합니다. 해당 출판사에 도서가 있는 경우 삭제를 거부합니다.
    @Transactional
    public void deletePublisher(Long id){
        publisherRepository.findById(id).map((pub) -> {
            publisherRepository.deleteById(id);
            return id;
        }).orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
