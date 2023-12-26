package com.xxz.model.artwork.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xzxie
 * @create 2023/12/17 16:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("作品上传DTO")
public class ArtworkUploadDTO {

    public String name;

    public String description;

    public Long classifyId;

    public String tags;

}
