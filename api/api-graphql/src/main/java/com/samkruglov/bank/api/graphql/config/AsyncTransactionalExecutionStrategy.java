package com.samkruglov.bank.api.graphql.config;

import graphql.ExecutionResult;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionStrategyParameters;
import graphql.execution.NonNullableFieldWasNullException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * Executes every request under a single transaction.
 */
// fixme add DataLoader and a cache layer before hitting the database
@Service
public class AsyncTransactionalExecutionStrategy extends AsyncExecutionStrategy {

    @Override
    @Transactional
    public CompletableFuture<ExecutionResult> execute(
            ExecutionContext executionContext,
            ExecutionStrategyParameters parameters) throws NonNullableFieldWasNullException {

        return super.execute(executionContext, parameters);
    }
}