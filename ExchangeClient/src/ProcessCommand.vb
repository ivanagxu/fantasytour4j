Sub Process(ByRef cmdItem As MailItem)

Dim olApp As Outlook.Application
    
    If InStr(1, cmdItem.Subject, "[Email notification from HP inbox]") > 0 And cmdItem.SenderEmailAddress = "ivan.xu.hp@gmail.com" Then
        'MsgBox cmdItem.SenderEmailAddress
        Dim beginIdx
        Dim endIdx
        Dim notedIdx
        Dim followedIdx
        
        Dim commandStr As String
        Dim emailsStr As String
        
        Dim notedAction As String
        Dim followedAction As String
        
        Dim followedEmails As String
        Dim notedEmails As String
        
        commandStr = cmdItem.Body
        
        beginIdx = InStr(1, commandStr, "<Begin>", vbTextCompare)
        endIdx = InStr(1, commandStr, "<End>", vbTextCompare)
        
        notedIdx = InStr(1, commandStr, "Noted", vbTextCompare)
        followedIdx = InStr(1, commandStr, "Followed", vbTextCompare)
        
        If endIdx > beginIdx And beginIdx > 0 Then
            emailsStr = Replace(Replace(Replace(Mid(commandStr, beginIdx, endIdx - beginIdx), "<Begin>" & Chr(13), ""), Chr(10), ""), "<br>" & Chr(13), Chr(13))
            'MsgBox emailsStr & "|"
        
            'Get Noted Email in command
            If notedIdx > 0 And notedIdx < beginIdx Then
                notedAction = Mid(commandStr, notedIdx)
                notedAction = Trim(Mid(notedAction, 1, InStr(1, notedAction, Chr(13), vbTextCompare)))
                'MsgBox notedAction
                
                Dim notedEmail As Variant
                'MsgBox InStr(1, UCase(notedAction), "NOTED ALL")
                If InStr(1, UCase(notedAction), "NOTED ALL") > 0 Then
                    notedEmail = Split(Trim(emailsStr), Chr(13))
                    Dim tmp As Variant
                    For Each tmp In notedEmail
                        If Len(tmp) > 2 Then
                            notedEmails = notedEmails & getA_zCharacter(tmp)
                            'MsgBox "Noted :" & tmp ' Add to list
                        End If
                    Next tmp
                Else
                    notedEmail = Split(Trim(Replace(notedAction, "Noted", "")), ",")
                    Dim tmp2 As Variant
                    For Each tmp2 In notedEmail
                        tmp2 = Replace(Trim(tmp2), Chr(13), "")
                        If InStr(1, emailsStr, "[" & Trim(tmp2) & "][") > 0 Then
                            'MsgBox tmp2
                            Dim val As String
                            tmp2 = "[" & Trim(tmp2) & "]["
                            val = getA_zCharacter(Mid(emailsStr, InStr(1, emailsStr, tmp2, vbTextCompare), InStr(InStr(1, emailsStr, tmp2, vbTextCompare), emailsStr, Chr(13)) - InStr(1, emailsStr, tmp2, vbTextCompare)))
                            notedEmails = notedEmails & val
                            'MsgBox val ' Add to list
                        End If
                    Next tmp2
                End If
            End If
            
            'Get Followed Email in command
            If followedIdx > 0 And followedIdx < beginIdx Then
                followedAction = Mid(commandStr, followedIdx)
                followedAction = Trim(Mid(followedAction, 1, InStr(1, followedAction, Chr(13), vbTextCompare)))
                'MsgBox UCase(followedAction)
                
                Dim followedEmail As Variant
                
                If InStr(1, UCase(followedAction), "FOLLOWED ALL") > 0 Then
                    
                    followedEmail = Split(Trim(emailsStr), Chr(13))
                    Dim tmp3 As Variant
                    For Each tmp3 In followedEmail
                        If Len(tmp3) > 2 Then
                            followedEmails = followedEmails & getA_zCharacter(tmp3)
                            'MsgBox "Followed :" & tmp3 ' Add to list
                        End If
                    Next tmp3
                Else
                    followedEmail = Split(Trim(Replace(followedAction, "Followed", "")), ",")
                    Dim tmp4 As Variant
                    For Each tmp4 In followedEmail
                        'MsgBox tmp4
                        tmp4 = Replace(Trim(tmp4), Chr(13), "")
                        If InStr(1, emailsStr, "[" & Trim(tmp4) & "][") > 0 Then
                            Dim val2 As String
                            tmp4 = "[" & Trim(tmp4) & "]["
                            val2 = getA_zCharacter(Mid(emailsStr, InStr(1, emailsStr, tmp4, vbTextCompare), InStr(InStr(1, emailsStr, tmp4, vbTextCompare), emailsStr, Chr(13)) - InStr(1, emailsStr, tmp4, vbTextCompare)))
                            followedEmails = followedEmails & val2
                            'MsgBox val2 ' Add to list
                        End If
                    Next tmp4
                End If
            End If
        End If
        
        'Process Action
        Dim olNs As Outlook.NameSpace
        Dim olFldr As Outlook.MAPIFolder
        Dim olItms As Outlook.Items
        Dim olMail As MailItem
        Dim oTmpItem As Variant
         
        Set olApp = New Outlook.Application
        Set olNs = olApp.GetNamespace("MAPI")
        Set olFldr = olNs.GetDefaultFolder(olFolderInbox)
        Set olItms = olFldr.Items
         
        For Each oTmpItem In olItms
            If TypeName(oTmpItem) = "MailItem" Then
                Set olMail = oTmpItem
                If olMail.UnRead And InStr(1, olMail.Subject, "[Email notification from HP inbox]") = 0 Then
                    Dim key As String
                    
                    Dim yearstr As String
                    Dim monthstr As String
                    Dim daystr As String
                    Dim hourstr As String
                    Dim minutestr As String
                    Dim secondstr As String
                    
                    yearstr = Format(year(olMail.SentOn), "0000")
                    monthstr = Format(month(olMail.SentOn), "00")
                    daystr = Format(day(olMail.SentOn), "00")
                    hourstr = Format(hour(olMail.SentOn), "00")
                    minutestr = Format(minute(olMail.SentOn), "00")
                    secondstr = Format(second(olMail.SentOn), "00")
                    
                    key = getA_zCharacter("[" & yearstr & "-" & monthstr & "-" & daystr & " " & hourstr & ":" & minutestr & ":" & secondstr & "][" & olMail.Subject & "]")
                    'key = getA_zCharacter(olMail.Subject)
                    
                    'MsgBox olMail.
                    
                    'MsgBox followedEmails
                    'MsgBox key
                    'MsgBox notedEmails & Chr(13) & key
                    If InStr(Replace(notedEmails, Chr(160), " "), Replace(key, Chr(160), " ")) > 0 Then
                        'MsgBox "Processing Noted: " & key
                        olMail.UnRead = False
                    End If
                    
                    If InStr(Replace(followedEmails, Chr(160), " "), Replace(key, Chr(160), " ")) > 0 Then
                        'MsgBox "Processing Followed: " & key
                        olMail.UnRead = False
                        'olMail.FlagStatus = olFlagMarked
                        olMail.MarkAsTask olMarkToday
                        olMail.TaskDueDate = Now()
                        olMail.TaskStartDate = Now()
                        olMail.FlagRequest = "Follow Up"
                        'olMail.FlagIcon = 6
                        olMail.Save
                    End If
                    
                End If
            End If
        Next oTmpItem
         
        cmdItem.UnRead = False
        cmdItem.Delete
        
        Set olFldr = Nothing
        Set olNs = Nothing
        Set olApp = Nothing
    End If
End Sub

Function getA_zCharacter(ByVal str As String) As String
    Dim res As String
    res = ""
    For i = 1 To Len(str)
        ch = Mid(str, i, 1)
        
        'If (ch <= "9" And ch >= "0") Or (ch <= "z" And ch >= "a") Or (ch <= "Z" And ch >= "A") Or ch = " ") Then
        If (ch <= "9" And ch >= "0") Or (ch <= "z" And ch >= "a") Or (ch <= "Z" And ch >= "A") Then
            res = res & ch
        Else
            res = res & " "
        End If
    Next
    getA_zCharacter = res
End Function

Sub TestMain()
    Dim olNs As Outlook.NameSpace
    Dim olFldr As Outlook.MAPIFolder
    Dim olItms As Outlook.Items
    Dim olMail As MailItem
    Dim oTmpItem As Variant
    
    Dim i As Long
     
    Set olApp = New Outlook.Application
    Set olNs = olApp.GetNamespace("MAPI")
    Set olFldr = olNs.GetDefaultFolder(olFolderInbox)
    Set olItms = olFldr.Items
     
    olItms.Sort "Subject"
     
    i = 1
     
    For Each oTmpItem In olItms
        If TypeName(oTmpItem) = "MailItem" Then
            Set olMail = oTmpItem
            Call Process(olMail)
        End If
    Next oTmpItem
     
    Set olFldr = Nothing
    Set olNs = Nothing
    Set olApp = Nothing
End Sub
