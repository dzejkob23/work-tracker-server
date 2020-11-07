package dev.jakubzika.worktracker.controler

import dev.jakubzika.worktracker.repository.WorkSessionRepository
import java.time.LocalDateTime

interface TrackingController {

    suspend fun startSession(userId: Long, projectId: Long)

    suspend fun endSession(userId: Long)

}
class TrackingControllerImpl(
        private val workSessionRepository: WorkSessionRepository
) : TrackingController {

    override suspend fun startSession(userId: Long, projectId: Long) {
        workSessionRepository.addWorkSession(
                LocalDateTime.now().toString(),
                "",
                userId,
                projectId
        )
    }

    override suspend fun endSession(userId: Long) {
        workSessionRepository.endWorkSession(userId)
    }
}