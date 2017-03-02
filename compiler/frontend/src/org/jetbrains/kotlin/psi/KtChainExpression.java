/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.lexer.KtTokens;

import java.util.List;

public class KtChainExpression extends KtExpressionImpl {
    public KtChainExpression(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public <R, D> R accept(@NotNull KtVisitor<R, D> visitor, D data) {
        return visitor.visitChainExpression(this, data);
    }

    @Nullable
    public KtExpression getSubjectExpression() {
        return findChildByClass(KtExpression.class);
    }

    @Nullable
    @IfNotParsed
    public PsiElement getChainKeyword() {
        //noinspection ConstantConditions
        return findChildByType(KtTokens.CHAIN_KEYWORD);
    }

    @NotNull
    public List<KtFunctionLiteral> getEntries() {
        return findChildrenByType(KtNodeTypes.FUNCTION_LITERAL);
    }

    @Nullable
    public PsiElement getCloseBrace() {
        return findChildByType(KtTokens.RBRACE);
    }

    @Nullable
    public PsiElement getOpenBrace() {
        return findChildByType(KtTokens.LBRACE);
    }
}
