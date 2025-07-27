package com.feelory.feelory_backend.words.docs;

public class DailyWordsDocs {

    public static final String POST_DAILY_WORD_DESCRIPTION = """
            오늘을 포함한 미래 날짜 중 하루를 지정하여 오늘의 단어를 지정하는 API
            
            ### Request
            - isReplaceApproved : 특정 날짜에 이미 오늘의 날짜가 배정되어 있을 경우, 덮어씌울지에 대한 flag
            
            ### Response
            - isAlreadyAssigned : 특정 날짜에 이미 오늘의 날짜가 배정되어 있음을 알리는 flag
            
            ### 흐름
            - isReplaceApproved를 false나 null로 전송
              - -> 오늘의 단어 중복 여부 확인
              - -> 중복될 경우 isAlreadyAssigned를 true로 Return
              - -> 프론트에서 단어 교체 "확인" 클릭
              - -> Request의 isReplaceApporved를 true로 전송
              - -> 서버에서 오늘의 단어 교체 실행
        """;
}
