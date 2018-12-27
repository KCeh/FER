import numpy as np 
import random
class GeneticAlgorithm(object): 
	"""
		Implement a simple generationl genetic algorithm as described in the instructions
	"""

	def __init__(	self, chromosomeShape,
					errorFunction,
					elitism = 1,
					populationSize = 25, 
					mutationProbability  = .1, 
					mutationScale = .5,
					numIterations = 10000, 
					errorTreshold = 1e-6
					): 

		self.populationSize = populationSize # size of the population of units
		self.p = mutationProbability # probability of mutation
		self.numIter = numIterations # maximum number of iterations
		self.e = errorTreshold # threshold of error while iterating
		self.f = errorFunction # the error function (reversely proportionl to fitness)
		self.keep = elitism  # number of units to keep for elitism
		self.k = mutationScale # scale of the gaussian noise

		self.i = 0 # iteration counter

		# initialize the population randomly from a gaussian distribution
		# with noise 0.1 and then sort the values and store them internally

		self.population = []
		for _ in range(populationSize):
			chromosome = np.random.randn(chromosomeShape) * 0.1

			fitness = self.calculateFitness(chromosome)
			self.population.append((chromosome, fitness))

		# sort descending according to fitness (larger is better)
		self.population = sorted(self.population, key=lambda t: -t[1])

	def step(self):	
		"""
			Run one iteration of the genetic algorithm. In a single iteration,
			you should create a whole new population by first keeping the best
			units as defined by elitism, then iteratively select parents from
			the current population, apply crossover and then mutation.

			The step function should return, as a tuple: 
				
			* boolean value indicating should the iteration stop (True if 
				the learning process is finished, False othwerise)
			* an integer representing the current iteration of the 
				algorithm
			* the weights of the best unit in the current iteration

		"""
		
		self.i += 1

		#############################
		#       YOUR CODE HERE      #
		#############################
		newPopulation = self.bestN(self.keep)

		while len(newPopulation) < self.populationSize:
			parent1, parent2 = self.selectParents()
			child = self.crossover(parent1, parent2)
			self.mutate(child)
			fitness = self.calculateFitness(child)
			newPopulation.append((child, fitness))

		self.population = newPopulation
		self.population = sorted(self.population, key=lambda t: -t[1])

		b = self.best()
		error = 1. / b[1]
		done = error<self.e or self.i>self.numIter

		return done, self.i, self.best()[0]

	def calculateFitness(self, chromosome):
		"""
			Implement a fitness metric as a function of the error of
			a unit. Remember - fitness is larger as the unit is better!
		"""
		#print "chromosome"
		#print chromosome
		chromosomeError = self.f(chromosome)
		#print "chromosomeError"
		#print chromosomeError[0]
		#############################
		#       YOUR CODE HERE      #
		#############################

		return 1./chromosomeError

	def bestN(self, n):
		"""
			Return the best n units from the population
		"""
		#############################
		#       YOUR CODE HERE      #
		#############################

		self.population = sorted(self.population, key=lambda t: -t[1])
		best = []
		for i in range(0, n):
			best.append(self.population[i])

		return best

		#return sorted(self.population, key=lambda t: -t[1])[:n]
	def best(self):
		"""
			Return the best unit from the population
		"""
		#############################
		#       YOUR CODE HERE      #
		#############################

		self.population = sorted(self.population, key=lambda t: -t[1])
		return self.population[0]

	def selectParents(self):
		"""
			Select two parents from the population with probability of 
			selection proportional to the fitness of the units in the
			population		
		"""
		#############################
		#       YOUR CODE HERE      #
		#############################
		sumOfFitness = 0
		#print "new iteratiron"
		for i in range(0, len(self.population)):
			unit=self.population[i]
			#print unit
			#print sumOfFitness
			sumOfFitness = unit[1] + sumOfFitness

		roulettWheelSelectionValue = np.random.uniform(0, sumOfFitness)
		#ako doljnja for petlja ne uspije pronaci
		parent1=self.population[0]
		parent2=self.population[0]

		#https://en.wikipedia.org/wiki/Fitness_proportionate_selection#Java_%E2%80%93_linear_O(n)_version
		for i in range(0,len(self.population)):
			roulettWheelSelectionValue -= self.population[i][1]
			if(roulettWheelSelectionValue<0):
				parent1 = self.population[i]
				break

		roulettWheelSelectionValue = np.random.uniform(0, sumOfFitness)
		#print "random"
		#print roulettWheelSelectionValue
		for i in range(0,len(self.population)):
			#print self.population[i]
			#print roulettWheelSelectionValue
			roulettWheelSelectionValue -= self.population[i][1]
			if(roulettWheelSelectionValue<=0):
				parent2=self.population[i]
				break

		'''
		print "parent selection"
		print parent1
		print parent2
		'''

		return parent1[0], parent2[0]

	def crossover(self, p1, p2): 
		"""
			Given two parent units p1 and p2, do a simple crossover by 
			averaging their values in order to create a new child unit
		"""
		#############################
		#       YOUR CODE HERE      #
		#############################

		#print "Crossover"
		#print p1
		#print p2
		#print np.average([p1, p2], axis=0)
		return np.average([p1, p2], axis=0)

	def mutate(self, chromosome):
		"""
			Given a unit, mutate its values by applying gaussian noise
			according to the parameter k
		"""

		#############################
		#       YOUR CODE HERE      #
		#############################
		#print "mutation"
		#print chromosome
		#return chromosome

		for i in range(0,len(chromosome)):
			if self.p<np.random.random_sample() :chromosome[i] += random.gauss(0, self.k)

