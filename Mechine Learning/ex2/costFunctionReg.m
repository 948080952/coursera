function [J, grad] = costFunctionReg(theta, X, y, lambda)
%COSTFUNCTIONREG Compute cost and gradient for logistic regression with regularization
%   J = COSTFUNCTIONREG(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta

J = - (sum(log(1 ./ (1 + exp(- X * theta))) .* y + log(1 - 1 ./ (1 + exp(- X * theta))) .* (1 - y)) - lambda * sum(theta(2:size(theta), 1) .^ 2) / 2) / m;

n = length(theta);

theta_t = theta;
theta_t(1, 1) = 0;

for i = 1:n
    grad(i, 1) = - (sum(X(:, i) .* y ./ ((1 + exp(X * theta)) .* log(exp(1))) + ...
    (y - 1) .* X(:, i) ./ ((1 + exp(X * theta)) .* log(exp(1)) .* exp(- X * theta)) ...
    ) - lambda * theta_t(i, 1)) / m;
end

% =============================================================

end
