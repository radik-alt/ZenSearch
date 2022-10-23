package com.five.zensearch.com.five.zensearch.data

import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import com.five.zensearch.com.five.zensearch.domain.model.PostModel

fun PostModel.toDTO() = PostDTO(
    id = id,
    title = title,
    address = address,
    description = description,
    tags = tags,
    image = image,
    isConfirmed = isConfirmed,
    date = date,
    creatorId = creatorId,
    participants = participants
)

fun PostDTO.toModel() = PostModel(
    id = id,
    title = title,
    address = address,
    description = description,
    tags = tags,
    image = image,
    isConfirmed = isConfirmed,
    date = date,
    creatorId = creatorId,
    participants = participants
)