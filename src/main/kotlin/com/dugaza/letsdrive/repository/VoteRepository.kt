package com.dugaza.letsdrive.repository

import com.dugaza.letsdrive.entity.community.Vote
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VoteRepository : JpaRepository<Vote, UUID>
