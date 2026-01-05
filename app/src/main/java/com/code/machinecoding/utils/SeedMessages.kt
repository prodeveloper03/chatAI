package com.code.machinecoding.utils

import com.code.machinecoding.data.local.MessageEntity

object SeedMessages {
    fun seed(): List<MessageEntity> {
        val base = listOf(
            MessageEntity("msg-001","Hi! I need help booking a flight to Mumbai.","TEXT",null,null,null,"USER",1703520000000),
            MessageEntity("msg-002","Hello! I'd be happy to help you book a flight to Mumbai. When are you planning to travel?","TEXT",null,null,null,"AGENT",1703520030000),
            MessageEntity("msg-003","Next Friday, December 29th.","TEXT",null,null,null,"USER",1703520090000),
            MessageEntity("msg-004","Great! And when would you like to return?","TEXT",null,null,null,"AGENT",1703520120000),
            MessageEntity("msg-005","January 5th. Also, I prefer morning flights.","TEXT",null,null,null,"USER",1703520180000),
            MessageEntity("msg-006","Perfect! Let me search for morning flights from your location to Mumbai. Could you also share your departure city?","TEXT",null,null,null,"AGENT",1703520210000),
            MessageEntity("msg-007","Bangalore. Here's a screenshot of my preferred airline.","FILE",
                "https://images.unsplash.com/photo-1436491865332-7a61a109cc05?w=400",
                245680,
                "https://images.unsplash.com/photo-1436491865332-7a61a109cc05?w=100",
                "USER",1703520300000),
            MessageEntity("msg-008","Thanks for sharing! I can see you prefer IndiGo. Let me find the best options for you.","TEXT",null,null,null,"AGENT",1703520330000),
            MessageEntity("msg-009","Flight options comparison","FILE",
                "https://images.unsplash.com/photo-1464037866556-6812c9d1c72e?w=400",
                189420,
                "https://images.unsplash.com/photo-1464037866556-6812c9d1c72e?w=100",
                "AGENT",1703520420000),
            MessageEntity("msg-010","The second option looks perfect! How do I proceed?","TEXT",null,null,null,"USER",1703520480000)
        )


        val extra = (11..25).map { i ->
            val id = "msg-%03d".format(i)
            val isUser = i % 2 == 1
            MessageEntity(
                id = id,
                message = if (isUser) "User follow-up message " else "Agent reply message ",
                type = "TEXT",
                filePath = null,
                fileSize = null,
                thumbnailPath = null,
                sender = if (isUser) "USER" else "AGENT",
                timestamp = 1703520480000 + (i * 60_000L)
            )
        }

        return base + extra
    }
}
