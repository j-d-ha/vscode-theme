package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.util.ObjectUtils
import com.jetbrains.rider.languages.fileTypes.csharp.kotoparser.lexer.CSharpTokenType

class CSharpAnnotator : BaseAnnotator() {
    companion object {
        val DEFAULT_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DEFAULT_KEYWORD"),
            DefaultLanguageHighlighterColors.KEYWORD
        )

        val SECONDARY_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "DEFAULT_SECONDARY_KEYWORD",
            DEFAULT_KEYWORD
        )

        private val PREPROCESSOR_CONDITIONALS = TokenSet.create(
            CSharpTokenType.PP_IF_SECTION,
            CSharpTokenType.PP_ELSE_SECTION,
            CSharpTokenType.PP_DEFAULT,
        )
    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        return when (element.text) {
            "return", "yield",
            "switch", "case", "break", "continue",
            "try", "catch", "finally", "throw",
            "for", "while", "do", "foreach" -> SECONDARY_KEYWORD

            "if", "else", "default" -> {
                if (PREPROCESSOR_CONDITIONALS.contains(element.node.elementType)) null
                else SECONDARY_KEYWORD
            }

            else -> null
        }
    }
}