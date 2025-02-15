package com.dugaza.letsdrive.controller

import com.dugaza.letsdrive.entity.user.User
import com.dugaza.letsdrive.integration.BaseIntegrationTest
import com.dugaza.letsdrive.service.user.UserService
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.imageio.ImageIO
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.`when`
import org.springframework.test.context.bean.override.mockito.MockitoBean

class FileControllerTest: BaseIntegrationTest() {

    @MockitoBean
    lateinit var userService: UserService

    /**
     * 테스트용으로 유효한 JPG 이미지를 메모리에서 생성하는 헬퍼 함수.
     */
    private fun createValidJpgImageBytes(width: Int, height: Int, color: Color): ByteArray {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()
        graphics.color = color
        graphics.fillRect(0, 0, width, height)
        graphics.dispose()
        val baos = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", baos)
        return baos.toByteArray()
    }

    /**
     * 파일 업로드 엔드포인트를 테스트한다.
     */
    @Test
    fun `uploadFile endpoint should upload file and return valid response`() {
        // 임의의 사용자 ID 생성 (실제 테스트에서는 UserService의 모킹 혹은 테스트 데이터가 필요)
        val userId = UUID.randomUUID()
        val dummyUser = mockk<User>(relaxed = true)
        every { userService.getUserById(userId) } returns dummyUser
        val imageBytes = createValidJpgImageBytes(100, 100, Color.BLUE)
        val multipartFile = MockMultipartFile("files", "test.jpg", "image/jpeg", imageBytes)

        val result = mockMvc.perform(
            multipart("/api/files/upload")
                .file(multipartFile)
                .param("userId", userId.toString())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.fileMasterId").exists())
            .andExpect(jsonPath("$.fileDetails").isArray)
            .andReturn()

        val responseContent = result.response.contentAsString
        println("Upload response: $responseContent")
    }

    /**
     * 파일 다운로드 엔드포인트를 테스트한다.
     * 업로드 후 생성된 FileDetail의 ID를 추출하여, 다운로드 요청 시 올바른 HTTP 헤더와 파일 내용이 반환되는지 검증한다.
     */
    @Test
    fun `downloadFile endpoint should return file with correct headers`() {
        val userId = UUID.randomUUID().toString()
        val imageBytes = createValidJpgImageBytes(100, 100, Color.GREEN)
        val multipartFile = MockMultipartFile("files", "download.jpg", "image/jpeg", imageBytes)

        // 파일 업로드를 먼저 수행하여 FileDetail을 생성한다.
        val uploadResult = mockMvc.perform(
            multipart("/api/files/upload")
                .file(multipartFile)
                .param("userId", userId)
        )
            .andExpect(status().isOk)
            .andReturn()

        // 업로드 응답에서 FileDetail의 ID를 추출 (JSON 파싱)
        val responseMap = objectMapper.readValue(uploadResult.response.contentAsString, Map::class.java)
        val fileDetails = responseMap["fileDetails"] as List<Map<String, Any>>
        val detailId = fileDetails.first()["id"].toString()

        // 다운로드 요청 (disposition: attachment) 및 응답 헤더와 내용 검증
        mockMvc.perform(get("/api/files/{detailId}/download/{dispositionType}", detailId, "attachment"))
            .andExpect(status().isOk)
            .andExpect(header().exists("Content-Disposition"))
            .andExpect(header().string("Content-Disposition", containsString("attachment")))
            .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE))
            .andExpect { result ->
                val downloadedBytes = result.response.contentAsByteArray
                assertArrayEquals(imageBytes, downloadedBytes, "다운로드한 파일 내용은 업로드한 이미지와 동일해야 합니다.")
            }
    }

    /**
     * 기본 프로필 이미지 엔드포인트를 테스트한다.
     * 테스트 환경에서는 기본 프로필 이미지의 레코드가 미리 등록되어 있어야 한다.
     */
    @Test
    fun `defaultProfileImage endpoint should return file inline`() {
        // 기본 이미지 엔드포인트는 application-test.yml에 지정된 default-image-detail-id를 사용한다.
        mockMvc.perform(get("/api/files/default-profile-image"))
            .andExpect(status().isOk)
            .andExpect(header().string("Content-Disposition", containsString("inline")))
            .andExpect { result ->
                val resource = ByteArrayResource(result.response.contentAsByteArray)
                assertTrue(resource.contentLength() > 0, "기본 프로필 이미지 파일 내용이 있어야 합니다.")
            }
    }
}