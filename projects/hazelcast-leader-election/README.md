# Hazelcast Leader Election

[![Build Status](https://img.shields.io/github/workflow/status/vanillaSlice/the-mono/Hazelcast%20Leader%20Election/main)](https://github.com/vanillaSlice/the-mono/actions?query=workflow%3AHazelcast-Leader-Election+branch%3Amain)
[![Coverage Status](https://img.shields.io/codecov/c/gh/vanillaSlice/the-mono/main?flag=HazelcastLeaderElection)](https://codecov.io/gh/vanillaSlice/the-mono/tree/main/projects/hazelcast-leader-election)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

Playing around with leadership election with [Hazelcast](https://hazelcast.com/) and
[Kubernetes](https://kubernetes.io/).

## Getting Started

To create the initial deployment run:

```
kubectl apply -f ./k8s.yml
```

Wait until all pods are ready and open up the proxy:

```
kubectl proxy
```

Then hit `http://127.0.0.1:8001/api/v1/namespaces/default/services/leader-election:7080/proxy/leader`.
This should respond with something like:

```json
{
  "leader": true,
  "ip": "172.17.0.7"
}
```

If you continually hit this endpoint, the response should stay the same. This is because all
requests are getting routed to the same pod (the leader).

You can find the leader pod by going to
`http://127.0.0.1:8001/api/v1/namespaces/default/endpoints/leader-election/`.
You can test a leader change by deleting the leader pod with something like:

```
kubectl delete pod leader-election-768c69b4f7-r56s4
```

Then hit `http://127.0.0.1:8001/api/v1/namespaces/default/services/leader-election:7080/proxy/leader` again.
This should respond with something different like:

```json
{
  "leader": true,
  "ip": "172.17.0.10"
}
```

To delete deployment:
```
kubectl delete -f ./k8s.yml
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
